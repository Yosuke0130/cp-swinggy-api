package com.example.demo.domain.user;

import java.sql.SQLException;


public interface UserRepository {

    public boolean insert(int userId, String name, String screenName, String email, String tel) throws SQLException;
}
