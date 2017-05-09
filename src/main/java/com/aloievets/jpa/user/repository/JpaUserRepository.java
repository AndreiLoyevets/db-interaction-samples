package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Created by Andrew on 07.05.2017.
 */
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void insert(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> find(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findByEmail(String email) {
        notEmpty(email);
        List resultList = entityManager
                .createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList();
        List<User> users = new ArrayList<>(resultList.size());
        resultList.forEach(user -> users.add((User) user));

        return users;
    }

    @Transactional
    @Override
    public void delete(long id) {
        Optional<User> user = find(id);
        user.ifPresent(entityManager::remove);
    }

    @Transactional
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
