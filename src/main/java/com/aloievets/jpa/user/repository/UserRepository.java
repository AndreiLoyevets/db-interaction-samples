package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;

import java.util.List;

/**
 * Created by Andrew on 01.05.2017.
 */
public interface UserRepository {

    List<User> findByEmail(String email);
}
