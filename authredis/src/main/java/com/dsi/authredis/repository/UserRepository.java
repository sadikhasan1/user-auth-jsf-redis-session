package com.dsi.authredis.repository;

import com.dsi.authredis.entity.User;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.Optional;

import static jakarta.ejb.TransactionAttributeType.REQUIRED;

@Stateless
public class UserRepository extends BaseRepository<User> {
    public UserRepository() {
        super();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @TransactionAttribute(REQUIRED)
    public void delete(User user) {
        if (getEntityManager().contains(user)) {
            getEntityManager().remove(user);
        } else {
            Optional<User> managedUser = findById(User.class, user.getId());
            managedUser.ifPresent(value -> getEntityManager().remove(value));
        }
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        TypedQuery<User> query = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber", User.class);
        query.setParameter("phoneNumber", phoneNumber);

        return executeForSingleResult(query);
    }

    public Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password) {
        TypedQuery<User> query = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND u.password = :password", User.class);
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("password", password);

        return executeForSingleResult(query);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        return executeForSingleResult(query);
    }

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);

        return executeForSingleResult(query);
    }
}