package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        addDefaultUser();
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(passwordCoder(user));
    }

    @Override
    public void removeUser(long id) {
        userDAO.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userDAO.getUserById(user.getUserId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.updateUser(user);
    }

    @Override
    public User getUserByLogin(String username) {
       return userDAO.getUserByLogin(username);
    }

    @Override
    public void addDefaultUser() {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleService.findById(1));
            Set<Role> roleSet2 = new HashSet<>();
            roleSet2.add(roleService.findById(1));
            roleSet2.add(roleService.findById(2));
            User user1 = new User("Garry", "Potter", (byte) 27, "user1@mail.ru", "user1", "12345", roleSet);
            User user2 = new User("Steve", "Jobs", (byte) 52, "admin@mail.ru", "admin", "admin", roleSet2);
            addUser(user1);
            addUser(user2);
    }
}