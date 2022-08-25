package com.kane.hotel.service;

import com.kane.hotel.dao.UserRepository;
import com.kane.hotel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User ajouter(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        if (u == null)
            return userRepository.save(user);
        else
            throw new ServiceException("L'adresse email existe déjà");
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User modifier(User u, Integer id) {
        User uD = userRepository.findById(id).get();
        uD.setFirstName(u.getFirstName());
        uD.setLastName(u.getLastName());
        uD.setEmail(u.getEmail());
        uD.setPicture(u.getPicture());
        uD.setIntroduction(u.getIntroduction());
        uD.setDescription(u.getDescription());
        return userRepository.save(uD);
    }

    @Override
    public User changePassword(User u, Integer id) {
        User uD = userRepository.findById(id).get();
        uD.setHash(u.getHash());
        return userRepository.save(uD);
    }
}
