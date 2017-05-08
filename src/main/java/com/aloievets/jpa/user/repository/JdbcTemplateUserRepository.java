package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Andrew on 02.05.2017.
 */
@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private static final String INSERT_QUERY = "INSERT INTO User (id, email) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT id, email FROM User WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT id, email FROM User WHERE email = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        notNull(user);
        jdbcTemplate.update(INSERT_QUERY, user.getId(), user.getEmail());
    }

    @Override
    public Optional<User> find(long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, this::toUser));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findByEmail(String email) {
        notNull(email);
        return jdbcTemplate.query(FIND_BY_EMAIL_QUERY, new Object[]{email}, this::toUser);
    }

    private User toUser(ResultSet resultSet, int row) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .build();
    }
}
