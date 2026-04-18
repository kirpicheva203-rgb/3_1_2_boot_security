package ru.kata.spring.boot_security.demo.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        return "admin";  // БЫЛО: return "users";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/admin/add")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                          @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("allRoles", roleRepository.findAll());
            return "admin";
        }
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roles);
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editForm(@RequestParam (value = "id", required = true) Long id, Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        model.addAttribute("editUser", userService.getUser(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", currentUser);
        return "edit";
    }

    @PostMapping("/admin/update")
    public String updateUser(@Valid @ModelAttribute("editUser") User user, BindingResult bindingResult,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("users", userService.getAllUsers());
            return "edit";
        }
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(roles);
        }
        userService.changeUser(user);
        return "redirect:/admin";
    }
}