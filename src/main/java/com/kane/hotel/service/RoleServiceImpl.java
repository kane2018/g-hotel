package com.kane.hotel.service;

import com.kane.hotel.dao.RoleRepository;
import com.kane.hotel.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role ajouterRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(String name) {
        return roleRepository.findByName(name);
    }
}
