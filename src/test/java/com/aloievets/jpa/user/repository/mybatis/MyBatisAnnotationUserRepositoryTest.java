package com.aloievets.jpa.user.repository.mybatis;

import com.aloievets.jpa.TestAppConfig;
import com.aloievets.jpa.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrew on 11.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class MyBatisAnnotationUserRepositoryTest {

    @Autowired
    private MyBatisAnnotationUserRepository userRepository;

    @Test
    public void find() {
        User expectedUser = User.builder().id(1L).email("user1@gmail.com").build();

        assertEquals(expectedUser, userRepository.find(1L));
    }

    @Test
    public void insert() {
        final long id = 987;
        User user = User.builder().id(id).email("user333@gmail.com").build();

        userRepository.insert(user);

        assertEquals(user, userRepository.find(id));
    }

    @Test
    public void update() {
        final long id = 987;
        User user = User.builder().id(id).email("user333@gmail.com").build();
        userRepository.insert(user);

        user.setEmail("ololo@ololo.com");
        userRepository.update(id, user.getEmail());

        assertEquals(user, userRepository.find(id));
    }
}