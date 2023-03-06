package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RoleRep;
import ru.kata.spring.boot_security.demo.DAO.UserRep;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRep userDAO;
    private final RoleRep roleDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRep userDAO, RoleRep roleDao, RoleService roleSer, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> userOptional = userDAO.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Override
    public void updateUser(User user) { userDAO.save(user); }

    @Override
    public void removeUser(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) { return userDAO.findByUsername(username); }

    @Override
    @PostConstruct
    public void addDefaultUser() {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleDAO.findById(1L).orElse(null));
            Set<Role> roleSet2 = new HashSet<>();
            roleSet2.add(roleDAO.findById(1L).orElse(null));
            roleSet2.add(roleDAO.findById(2L).orElse(null));
            System.out.println(roleSet);
            System.out.println(roleSet2);
            User user1 = new User("Leonid", "Drozd", (byte) 29, "user1@mail.ru", "user1", "12345", roleSet);
            User user2 = new User("SuperAdmin", "Adminovich", (byte) 69, "admin@mail.ru", "admin", "admin", roleSet2);
            addUser(user1);
            addUser(user2);
    }
}