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

    E toOrigin();

    D toResource(E origin);

    default List<D> toResources(List<E> origins) {
        List<D> resources = new ArrayList<>();

        for (E origin : origins) {
            resources.add(toResource(origin));
        }

        return resources;
    }
}