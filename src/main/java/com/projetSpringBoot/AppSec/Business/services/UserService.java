package com.projetSpringBoot.AppSec.Business.services;

import com.projetSpringBoot.AppSec.DAO.entities.User;

import java.util.Optional;

public interface UserService {
    public User registerUser(User user);
    public Optional<User> findByEmail(String email);
}
