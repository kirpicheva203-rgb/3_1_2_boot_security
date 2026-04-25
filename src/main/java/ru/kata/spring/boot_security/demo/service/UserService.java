package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User findUserById(Long id);

    void addUser(User user, List<Long> roleIds);

    void deleteUser(Long id);

    void updateUser(User user);

    User findByEmail(String Email);

    List<Role> getAllRoles();
}