package com.hotspice.objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request<T> implements Serializable {
    private T payload;
    private String clientInfo;
    private String appVersion;
    private String comments;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Request{" +
                "payload=" + payload +
                ", clientInfo='" + clientInfo + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
