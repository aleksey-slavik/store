package com.globallogic.store.dao;

public class CustomCriteria {

    private int offset;
    private int limit;
    private String sortBy;
    private String orderBy;

    public CustomCriteria(int page, int size, String sortBy, String orderBy) {
        this.offset = (page - 1) * size;
        this.limit = size;
        this.sortBy = sortBy;
        this.orderBy = orderBy;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
