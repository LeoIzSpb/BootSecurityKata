package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("admin")
    public String pageForAdmin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("admin/new")
    public String pageCreateUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles",roleService.getAllRoles());
        return "create";
    }

    @PostMapping("admin/new")
    public String pageCreate(@ModelAttribute("user")
                             @Valid User user, BindingResult bindingResult,
                             @RequestParam("listRoles") ArrayList<Long> roles) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        if (userService.getUserByLogin(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username",
                    String.format("User with name \"%s\" is already exist!", user.getUsername())));
            return "create";
        }
        user.setRoles(roleService.findByIdRoles(roles));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/delete")
    public String pageDelete(@RequestParam(value = "id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit")
    public String pageEditUser(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("admin/edit")
    public String pageEdit(@Valid User user, BindingResult bindingResult,
                           @RequestParam("listRoles") ArrayList<Long>roles) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        user.setRoles(roleService.findByIdRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}