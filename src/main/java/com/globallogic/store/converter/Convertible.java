package com.globallogic.store.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for covert objects to their DTO
 *
 * @param <E> original entity
 * @param <D> DTO entity
 * @author oleksii.slavik
 */
public interface Convertible<E, D> {

    /**
     * Convert data transfer object to entity
     *
     * @param dto data transfer object
     * @return entity object
     */
    E toOrigin(D dto);

    /**
     * Convert entity to data transfer object
     *
     * @param origin entity object
     * @return data transfer object
     */
    D toResource(E origin);

    /**
     * Convert list of entities to list of data transfer objects
     *
     * @param origins list of entities
     * @return list of data transfer objects
     */
    default List<D> toResources(List<E> origins) {
        List<D> resources = new ArrayList<>();

        for (E origin : origins) {
            resources.add(toResource(origin));
        }

        return resources;
    }
}