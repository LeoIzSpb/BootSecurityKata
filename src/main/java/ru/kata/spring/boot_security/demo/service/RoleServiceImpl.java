package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RoleRep;
import ru.kata.spring.boot_security.demo.model.Role;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRep roleDAO;

    @Autowired
    public RoleServiceImpl(RoleRep roleDAO) {
        this.roleDAO = roleDAO;
        addDefaultRole();
    }

    @Override
    public List<Role> getAllRoles() { return roleDAO.findAll(); }

        @Override
    public Set<Role> findByIdRoles(List<Long> roles) { return new HashSet<>(roleDAO.findAllById(roles)); }

    @Override
    public void addDefaultRole() {
        roleDAO.save(new Role("ROLE_USER"));
        roleDAO.save(new Role("ROLE_ADMIN"));
    }
}