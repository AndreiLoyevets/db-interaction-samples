package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.TestAppConfig;
import com.aloievets.jpa.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrew on 11.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class MyBatisUserRepositoryTest {

    @Autowired
    private MyBatisUserRepository userRepository;

    @Test
    public void find() {
        User user = User.builder().id(1L).email("user1@gmail.com").build();

        assertEquals(user, userRepository.find(1L));
    }

    @Test
    public void findByEmail() {
        assertEquals(2, userRepository.findByEmail("user2@gmail.com").size());
    }

    @Test
    public void insert() {
        final long id = 777;
        User user = User.builder().id(id).email("email@email.com").build();

        userRepository.insert(user);

        assertEquals(user, userRepository.find(id));
    }

    @Test
    public void delete() {
        final long id = 7778;
        User user = User.builder().id(id).email("email@email.com").build();

        userRepository.insert(user);
        userRepository.delete(id);

        assertNull(userRepository.find(id));
    }

    @Test
    public void findByEmails() {
        List<String> emails = Arrays.asList("user1@gmail.com", "user2@gmail.com");

        assertEquals(3, userRepository.findByEmails(emails).size());
    }

    @Test
    public void update() {
        final long id = 999;
        User user = User.builder().id(id).email("999@gmail.com").build();
        userRepository.insert(user);
        user.setEmail("haha@haha");

        userRepository.update(user);

        assertEquals(user, userRepository.find(id));
    }
}
