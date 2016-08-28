package com.hotspice.dao;

import com.hotspice.entities.Category;
import com.hotspice.entities.OrderDetail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by Geetanshu on 25/08/16.
 */
@Repository
@Transactional(readOnly = false)
public class OrderDetailDao extends BaseDao<OrderDetail> {

    Logger logger = LoggerFactory.getLogger(OrderDetailDao.class);

    @Transactional
    public void createOrder(OrderDetail orderDetail)  {
        try {
            persist(orderDetail);
        } catch (Exception ex) {
            logger.error("Error in persisting order: " + ex + " ; "+ ex.getMessage());
        }
    }

    @Transactional
    public void updateOrder(OrderDetail orderDetail)  {
        try {
            update(orderDetail);
        } catch (Exception ex) {
            logger.error("Error in persisting order: " + ex + " ; "+ ex.getMessage());
        }
    }

    public OrderDetail getOrder(Long orderId) {
        try {
            OrderDetail orderDetail = entityManager.find(OrderDetail.class, orderId);
            return orderDetail;
        } catch (Exception ex) {
            logger.error("Error in getting order: " + ex + " ; "+ ex.getMessage());
        }
        return null;
    }

    public List<OrderDetail> searchOrder(String status, Date startDate, Date endDate) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> criteriaQuery = criteriaBuilder.createQuery(OrderDetail.class);
            Root<OrderDetail> from = criteriaQuery.from(OrderDetail.class);
            from.alias("orderDetailAlias");
            criteriaQuery.select(from);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            if(StringUtils.isNotBlank(status)){
                Predicate predicate = criteriaBuilder.equal(from.get("orderStatus"),status);
                predicateList.add(predicate);
            }

            if(startDate != null){
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(from.<Date>get("createdDate"), startDate);
                predicateList.add(predicate);
            }

            if(endDate != null){
                Predicate predicate = criteriaBuilder.lessThanOrEqualTo(from.<Date>get("createdDate"), endDate);
                predicateList.add(predicate);
            }

            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            TypedQuery<OrderDetail> typedQuery = entityManager.createQuery(criteriaQuery);
            List<OrderDetail> orderList = typedQuery.getResultList();
            return orderList;
        } catch (Exception ex) {
            logger.error("Error in getting order: " + ex + " ; "+ ex.getMessage());
        }
        return null;
    }
}
