package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.TestAppConfig;
import com.aloievets.jpa.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 01.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
abstract public class AbstractUserRepositoryTest {

    protected UserRepository userRepository;

    @Test
    public void insert() {
        long id = 200;
        User expectedUser = User.builder().id(id).email("ahaha@gmail.com").build();

        userRepository.insert(expectedUser);
        Optional<User> actualUser = userRepository.find(id);

        assertTrue(actualUser.isPresent());
        actualUser.ifPresent(user -> assertEquals(expectedUser, user));

        userRepository.delete(id);
    }

    @Test
    public void update() {
        final long id = 4;
        User user = userRepository.find(id).get();
        String oldEmail = user.getEmail();
        String newEmail = oldEmail + "a";

        user.setEmail(newEmail);
        userRepository.update(user);

        assertEquals(user, userRepository.find(id).get());
    }

    @Test
    public void delete() {
        long id = 300;
        User user = User.builder().id(id).email("ahahaha@gmail.com").build();
        userRepository.insert(user);

        userRepository.delete(id);

        assertFalse(userRepository.find(id).isPresent());
    }

    @Test
    public void findById() {
        User expectedUser = User.builder().id(1L).email("user1@gmail.com").build();

        Optional<User> actualUser = userRepository.find(1L);

        assertTrue(actualUser.isPresent());
        actualUser.ifPresent(user -> assertEquals(expectedUser, user));
    }

    @Test
    public void findByIdInvalidId() {
        assertFalse(userRepository.find(100500L).isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void findByEmailNullEmail() {
        userRepository.findByEmail(null);
    }

    @Test
    public void findByEmailSingleResult() {
        final String email = "user1@gmail.com";
        User expectedUser = User.builder().id(1L).email(email).build();

        assertEquals(expectedUser, userRepository.findByEmail(email).get(0));
    }

    @Test
    public void findByEmailMultipleResults() {
        final String email = "user2@gmail.com";
        List<User> expectedUsers = new ArrayList<>(2);
        expectedUsers.add(User.builder().id(2L).email(email).build());
        expectedUsers.add(User.builder().id(3L).email(email).build());

        List<User> actualUsers = userRepository.findByEmail(email);

        assertTrue(expectedUsers.containsAll(actualUsers));
        assertTrue(actualUsers.containsAll(expectedUsers));
    }
}