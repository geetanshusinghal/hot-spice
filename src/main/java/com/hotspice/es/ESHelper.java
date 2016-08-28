package com.hotspice.es;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ESHelper {

	private static final Logger logger = LoggerFactory.getLogger(ESHelper.class);

	private NodeBuilder nodeBuilderClient = null;
	private Node node = null;
	private Client client = null;

	@Autowired
	private Environment env;



	private void initiateTransportClient() {
		logger.info("intitializing transport client");
		try {
			String masterNodes = env.getProperty("elasticsearch.data.serverPort");
			String[] nodes = masterNodes.split(":");

			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodes[0]), Integer.parseInt(nodes[1])));
			logger.info("successfully intitialized transport client");
		} catch (Throwable e) {
			logger.info("Error while intitialized transport client");
			return;
		}
	}

	public ActionResponse indexDocument(String index, String type, String id, String json) {
		try {
			return getClient().prepareIndex(index, type, id).setSource(json).execute().actionGet();
		} catch (Throwable e) {
			logger.error("Exception occured for Es " + e.getMessage());
		}
		return null;
	}

	public UpdateResponse updateDocument(String index, String type, String id, Map<String, String> map) {
		try {
			return getClient().prepareUpdate(index, type, id).setDoc(map).setRefresh(true).execute().actionGet();
		} catch (Throwable e) {
			logger.error("Exception occured for Es " + e.getMessage());
		}
		return null;
	}

	public String getDocument(String index, String type, String id) {
		try {
			return getClient().prepareGet(index, type, id).execute().actionGet().getSourceAsString();
		} catch (Throwable e) {
			logger.error("Exception occured for Es " + e.getMessage());
		}
		return null;
	}

	public SearchHit[] searchDocumentWithKeyValuePair(String index, String type, Map<String, String> map,
								  List<String> sortByList, int startIndex, int endIndex) {
		Set<Map.Entry<String,String>> set = map.entrySet();
		try {
			BoolQueryBuilder bqd = QueryBuilders.boolQuery();

			for(Map.Entry<String,String> e : set){
				bqd.must(QueryBuilders.matchQuery(e.getKey(), e.getValue()));


			}
			SearchRequestBuilder request = getClient().prepareSearch(index).setTypes(type)
					.setFrom(startIndex).setSize(endIndex)
					.setSearchType(SearchType.QUERY_AND_FETCH).setQuery(bqd)
					.addSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC));
					for(String sortBy : sortByList) {
						request.addSort(SortBuilders.fieldSort(sortBy).order(SortOrder.DESC).missing("_last"));
					}
			SearchResponse response = request.setExplain(true).execute().actionGet();

			return response.getHits().getHits();
		} catch (Throwable e) {
			logger.error("Exception occured for Es " + e.getMessage());
		}
		return null;
	}

	private Client getClient() {
		if(client == null) {
			initiateTransportClient();
		}
		return client;
	}
}