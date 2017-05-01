package com.aloievets.jpa.user.repository;

import com.aloievets.jpa.user.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Andrew on 01.05.2017.
 */
@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String FIND_BY_EMAIL_QUERY = "SELECT id, email FROM User WHERE email = ?";

    @Autowired
    private DataSource dataSource;

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
                users.add(
                        User
                                .builder()
                                .id(resultSet.getLong("id"))
                                .email(resultSet.getString("email"))
                                .build()
                );
            }
            return users;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
