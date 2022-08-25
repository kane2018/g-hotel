package com.kane.hotel.service;

import com.kane.hotel.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User ajouter(User user);

    User getUser(Integer id);

    User getUserByEmail(String email);

    User modifier(User u, Integer id);

    User changePassword(User u, Integer id);
}
