package by.makei.spring.integration.service;

import by.makei.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    void test(){

    }

}
