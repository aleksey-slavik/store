package com.globallogic.store.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCriteria {

    private Map<String, List<Object>> params;
    private int offset;
    private int limit;
    private String orderBy;
    private String order;

    public SearchCriteria() {
        this(1, 5, "id", "desc");
    }

    public SearchCriteria(int page, int size, String orderBy, String order) {
        this.params = new HashMap<>(0);
        this.offset = (page - 1) * size;
        this.limit = size;
        this.orderBy = orderBy;
        this.order = order;
    }

    private void criteria(String field, Object value) {
        if (field != null && value != null && params != null) {
            List<Object> values;

            if (params.containsKey(field)) {
                values = params.get(field);
            } else {
                values = new ArrayList<>();
            }

            values.add(value);
            params.put(field, values);
        }
    }

    public SearchCriteria criteria(String field, Object... values) {
        if (field != null && values != null && params != null) {
            for (Object value : values) {
                criteria(field, value);
            }
        }

        return this;
    }

    public SearchCriteria offset(int offset) {
        this.offset = offset;
        return this;
    }

    public SearchCriteria limit(int limit) {
        this.limit = limit;
        this.offset = (offset - 1) * limit;
        return this;
    }

    public SearchCriteria sortBy(String sortBy) {
        this.orderBy = sortBy;
        return this;
    }

    public SearchCriteria order(String order) {
        this.order = order;
        return this;
    }

    public Map<String, List<Object>> getParams() {
        return params;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "params=" + params +
                ", offset=" + offset +
                ", limit=" + limit +
                ", orderBy='" + orderBy + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
