package com.aloievets.jpa.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Andrew on 08.05.2017.
 */
public class JdbcUserRepositoryTest extends AbstractUserRepositoryTest {

    @Autowired
    @Qualifier("jdbcUserRepository")
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
