package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.Role;


import java.util.List;
import java.util.Set;

public interface RoleDAO {
    List<Role> getAllRoles ();
    void addRole(Role role);
    Role getRole(String role);
    Role findById(long id);
    Set<Role> findByIdRoles(List<Long>roles);
}