package com.zhaoming.bean.platfrom.document;

import java.util.List;

/**
 * 对服务分组
 * @author hhx
 */
public class GroupDocument {

    private String serviceName;

    private List<PlatformDocument> documents;

    public GroupDocument() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<PlatformDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<PlatformDocument> documents) {
        this.documents = documents;
    }
}
