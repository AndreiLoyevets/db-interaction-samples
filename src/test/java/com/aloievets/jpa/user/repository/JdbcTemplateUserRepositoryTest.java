package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.TestAppConfig;
import com.aloievets.jpa.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrew on 02.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class JdbcTemplateUserRepositoryTest {

    @Autowired
    @Qualifier("jdbcTemplateUserRepository")
    private UserRepository userRepository;

    @Test
    public void findByEmailSingleResult() {
        final String email = "user1@gmail.com";
        User expectedUser = User.builder().id(1L).email(email).build();

        assertEquals(expectedUser, userRepository.findByEmail(email).get(0));
    }
}