package com.kane.hotel.service;

import com.kane.hotel.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role ajouterRole(Role role);
    Role getRole(String name);
}
