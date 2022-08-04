package by.makei.junit.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    long id;
    String name;
    String password;
}
