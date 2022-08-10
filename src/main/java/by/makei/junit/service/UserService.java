package by.makei.junit.service;

import by.makei.junit.dao.UserDao;
import by.makei.junit.dto.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService {
    private final UserDao userDao;


    private final List<User> users = new ArrayList<>();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll() {
        return users;
    }

    public boolean add(User...users) {
        return this.users.addAll(Arrays.asList(users));
    }

    public boolean delete(Long userId){
        return userDao.delete(++userId);
    }

    public Optional<User> login(String name, String password) {
        if(name == null || password == null){
            System.out.println("username or password is null");
            throw new IllegalArgumentException();
        }
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    public Map<Long, User> getAllConvertedById() {
        return users.stream().collect(Collectors.toMap(User::getId, Function.identity())); //Function.identity() - return t -> t;
    }
}
