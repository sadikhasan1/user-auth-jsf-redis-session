package com.dsi.authredis.repository;

import com.dsi.authredis.entity.BaseEntity;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import static jakarta.ejb.TransactionAttributeType.REQUIRED;

public class BaseRepository<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseRepository() {

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @TransactionAttribute(REQUIRED)
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @TransactionAttribute(REQUIRED)
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(REQUIRED)
    public void update(Iterable<T> entities) {
        entities.forEach(entityManager::merge);
    }

    @TransactionAttribute(REQUIRED)
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public Optional<T> findById(Class<T> entityClass, Long id) {
        try {
            return Optional.ofNullable(entityManager.find(entityClass, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<List<T>> findAll(Class<T> entityClass) {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);

        return executeForResultList(query);
    }

    public Optional<List<T>> findAllWithPagination(Class<T> entityClass, int firstResult, int maxResults) {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);

        // Set the first result (starting index) and the maximum number of results to retrieve
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return executeForResultList(query);
    }

    public Optional<T> executeForSingleResult(TypedQuery<T> query) {
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<List<T>> executeForResultList(TypedQuery<T> query) {
        try {
            return Optional.of(query.getResultList());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
