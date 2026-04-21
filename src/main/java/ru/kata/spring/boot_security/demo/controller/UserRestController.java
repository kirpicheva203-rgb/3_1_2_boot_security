package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoleRepository roleRepository;

    public UserRestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<User> allUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @PostMapping
    public User addNewUser(@RequestBody User user){
        userService.addUser(user);
        return user;
    }

    @PutMapping
    public void changeUser(@RequestBody User user){
        userService.changeUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

}
