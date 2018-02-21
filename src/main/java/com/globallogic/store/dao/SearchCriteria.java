package com.globallogic.store.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCriteria {

    private Map<String, List<Object>> params;
    private int offset;
    private int limit;
    private String sortBy;
    private String orderBy;

    public SearchCriteria() {
        this(1, 5, "id", "desc");
    }

    public SearchCriteria(int page, int size, String sortBy, String orderBy) {
        this.params = new HashMap<>(0);
        this.offset = (page - 1) * size;
        this.limit = size;
        this.sortBy = sortBy;
        this.orderBy = orderBy;
    }

    public void addCriteria(String field, Object value) {
        if (field !=  null && value != null && params != null) {
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

    public void addCriteria(String field, Object[] values) {
        if (field !=  null && values != null && params != null) {
            for (Object value : values) {
                addCriteria(field, value);
            }
        }
    }

    public boolean isParamsAbsent() {
        return params.isEmpty();
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

    public String getSortBy() {
        return sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
