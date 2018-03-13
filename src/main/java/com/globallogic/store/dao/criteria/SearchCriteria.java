package com.globallogic.store.dao.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search criteria object which consist query parameters
 *
 * @author oleksii.slavik
 */
public class SearchCriteria {

    /**
     * query parameters
     */
    private Map<String, List<Object>> params = new HashMap<>(0);

    /**
     * offset query value
     */
    private int offset;

    /**
     * limit query value
     */
    private int limit;

    /**
     * name of column for order by
     */
    private String orderBy;

    /**
     * order by query value
     */
    private String order;

    /**
     * Add criteria to given field
     *
     * @param field given field name
     * @param value given field value
     */
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

    /**
     * Add several criteria to given field
     *
     * @param field given field name
     * @param values array of field values
     */
    public SearchCriteria criteria(String field, Object... values) {
        if (field != null && values != null && params != null) {
            for (Object value : values) {
                criteria(field, value);
            }
        }

        return this;
    }

    /**
     * Set offset query value
     *
     * @param offset offset query value
     */
    public SearchCriteria offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set limit query value
     *
     * @param limit limit query value
     */
    public SearchCriteria limit(int limit) {
        this.limit = limit;
        this.offset = (offset - 1) * limit;
        return this;
    }

    /**
     * Set name of column for order by
     *
     * @param sortBy name of column for order by
     */
    public SearchCriteria sortBy(String sortBy) {
        this.orderBy = sortBy;
        return this;
    }

    /**
     * Set order by query value
     *
     * @param order order by query value
     */
    public SearchCriteria order(String order) {
        this.order = order;
        return this;
    }

    /**
     * @return query parameters
     */
    public Map<String, List<Object>> getParams() {
        return params;
    }

    /**
     * @return query offset value
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return query limit value
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @return name of column for order by
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @return order by query value
     */
    public String getOrder() {
        return order;
    }
}
