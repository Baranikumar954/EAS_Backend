package com.example.EAS_Backend.service;

import com.example.EAS_Backend.model.AdminUsers;
import com.example.EAS_Backend.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUsers admin = repo.findByUserName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(admin.getUserName(), admin.getPassword(), Collections.emptyList());
    }
}
