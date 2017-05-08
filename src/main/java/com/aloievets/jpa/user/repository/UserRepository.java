package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Andrew on 01.05.2017.
 */
public interface UserRepository {

    void insert(User user);

    Optional<User> find(long id);

    List<User> findByEmail(String email);
}
