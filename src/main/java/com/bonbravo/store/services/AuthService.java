package com.bonbravo.store.services;

import com.bonbravo.store.entities.User;
import com.bonbravo.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = Long.valueOf(authentication.getPrincipal().toString());
        System.out.println("User ID: " + userId);
        return userRepository.findById(userId).orElse(null);
    }
}
