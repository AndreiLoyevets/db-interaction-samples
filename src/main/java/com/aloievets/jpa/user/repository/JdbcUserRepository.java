package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Andrew on 01.05.2017.
 */
@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT_QUERY = "INSERT INTO User (id, email) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT id, email FROM User WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT id, email FROM User WHERE email = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    @SneakyThrows
    public void insert(User user) {
        notNull(user);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> find(long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = toUser(resultSet);
            }

            return Optional.ofNullable(user);
        } finally {
            close(preparedStatement, connection);
        }
    }

    @Override
    @SneakyThrows
    public List<User> findByEmail(String email) {
        notNull(email);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_QUERY);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new LinkedList<>();
            while (resultSet.next()) {
                users.add(toUser(resultSet));
            }

            return users;
        } finally {
            close(preparedStatement, connection);
        }
    }

    private User toUser(ResultSet resultSet) throws SQLException {
        return User
                .builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .build();
    }

    private void close(PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
