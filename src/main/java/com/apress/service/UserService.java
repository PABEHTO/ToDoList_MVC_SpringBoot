package com.apress.service;

import com.apress.entity.User;
import com.apress.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getTheUser() {
        String login = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository
                .findByLoginIgnoreCase(login)
                .orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );
    }

}
