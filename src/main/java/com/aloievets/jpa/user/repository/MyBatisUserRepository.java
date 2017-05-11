package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;

import java.util.List;

/**
 * Created by Andrew on 11.05.2017.
 */
public interface MyBatisUserRepository {

    User find(long id);

    List<User> findByEmail(String email);

    void insert(User user);

    void delete(long id);

    List<User> findByEmails(List<String> emails);

    void update(User user);
}
