package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    void addUser(User user);

    void deleteUser(Long id);

    void changeUser(User user);

    User findByUsername(String username);
}
