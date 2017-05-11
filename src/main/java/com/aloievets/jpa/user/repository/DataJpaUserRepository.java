package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Andrew on 11.05.2017.
 */
public interface DataJpaUserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
}
