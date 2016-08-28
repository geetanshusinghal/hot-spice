package com.hotspice.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao<T> {
    @PersistenceContext(unitName = "hotSpiceDefault")
    EntityManager entityManager;

   public void persist(T obj) {
        entityManager.persist(obj);
    }

    T update(T obj) {
       return entityManager.merge(obj);
    }

}
