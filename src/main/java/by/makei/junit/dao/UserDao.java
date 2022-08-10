package by.makei.junit.dao;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class UserDao {

    @SneakyThrows
    public boolean delete (Long userId){
        try (Connection connection = DriverManager.getConnection("url", "username", "password")) {
            return true;
        }
    }
}
