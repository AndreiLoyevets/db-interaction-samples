package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Andrew on 07.05.2017.
 */
@Repository
public class JpaUserRepository implements UserRepository {

    @Override
    public void insert(User user) {

    }

    @Override
    public Optional<User> find(long id) {
        return null;
    }

    @Override
    public List<User> findByEmail(String email) {
        return null;
    }
}
