package com.example.eclasswebapp;

import com.example.eclasswebapp.mysql.role.Role;
import com.example.eclasswebapp.mysql.user.User;
import com.example.eclasswebapp.mysql.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        int count = userRepository.doesUserExist("admin");
        if (count == 0) {
            User admin = new User();
            Role role = new Role();
            role.setId(1);
            admin.setUsername("admin");
            admin.setPassword("admin");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);
            admin.setVerified(true);
            admin.setRole(role);
            userRepository.save(admin);
        }
    }

}
