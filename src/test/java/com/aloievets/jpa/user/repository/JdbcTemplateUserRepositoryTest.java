package com.aloievets.jpa.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Andrew on 02.05.2017.
 */
public class JdbcTemplateUserRepositoryTest extends AbstractUserRepositoryTest {

    @Autowired
    @Qualifier("jdbcTemplateUserRepository")
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}