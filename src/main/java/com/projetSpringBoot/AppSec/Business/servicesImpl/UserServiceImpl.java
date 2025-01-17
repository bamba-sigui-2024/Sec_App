package com.projetSpringBoot.AppSec.Business.servicesImpl;

import com.projetSpringBoot.AppSec.Business.services.UserService;
import com.projetSpringBoot.AppSec.DAO.entities.User;
import com.projetSpringBoot.AppSec.DAO.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Encodez le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOauth2(false);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
