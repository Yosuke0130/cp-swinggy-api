package com.example.demo.infrastructure.user;

import com.example.demo.Logging;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    Logging logger;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public boolean insert(int userId, String name, String screenName, String email, String tel) throws SQLException {
        try {
            int user_count = jdbc.update("insert into user(user_id) values(?)", userId);

            int user_profile_count = jdbc.update(
                        "insert into user_profile(user_profile_id, user_id, name, screen_name, email, tel)" +
                            " values(?, ?, ?, ?, ?, ?)",
                            userId, userId, name, screenName, email, tel);

            logger.info("user_result : " + user_count + ", user_profile_result : " + user_profile_count);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("db error");

        }
    }
}
