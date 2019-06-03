package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.exceptions.UserNotFoundException;
import com.yudystriawan.complaintserver.models.Role;
import com.yudystriawan.complaintserver.models.RoleName;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.models.request.LoginForm;
import com.yudystriawan.complaintserver.models.request.RegisterForm;
import com.yudystriawan.complaintserver.repositories.RoleRepository;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody User user) {
        User getUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Account not found"));

        if (passwordEncoder.matches(user.getPassword(), getUser.getPassword())) {
            return ResponseEntity.ok().body(getUser);
        } else {
            return new ResponseEntity<>("Username or password not valid", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exist");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exist");
        }

        User newUser = new User(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword())
        );

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role tidak ada"));
        newUser.setRole(role);
        newUser.setEnabled(true);

        userRepository.save(newUser);

        return ResponseEntity.ok("Registration success");

    }


}
