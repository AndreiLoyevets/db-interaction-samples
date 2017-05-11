package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.TestAppConfig;
import com.aloievets.jpa.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrew on 11.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class DataJpaUserRepositoryTest {

    @Autowired
    private DataJpaUserRepository userRepository;

    @Test
    public void insert() {
        final long id = 333;
        User user = User.builder().id(id).email("333@gmail.com").build();
        userRepository.save(user);

        assertEquals(user, userRepository.findOne(id));
    }

    @Test
    public void findByEmail() {
        List<User> users = userRepository.findByEmail("user2@gmail.com");

        assertEquals(2, users.size());
    }
}
