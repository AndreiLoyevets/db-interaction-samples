package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Andrew on 02.05.2017.
 */
@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private static final String FIND_BY_EMAIL_QUERY = "SELECT id, email FROM User WHERE email = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findByEmail(String email) {
        notNull(email);
        return jdbcTemplate.query(FIND_BY_EMAIL_QUERY, new Object[]{email},
                (resultSet, rowNumber) ->
                        User.builder()
                                .id(resultSet.getLong("id"))
                                .email(resultSet.getString("email"))
                                .build());
    }
}
