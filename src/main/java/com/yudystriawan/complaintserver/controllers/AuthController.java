package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.models.Role;
import com.yudystriawan.complaintserver.models.RoleName;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.models.request.LoginForm;
import com.yudystriawan.complaintserver.models.request.RegisterForm;
import com.yudystriawan.complaintserver.repositories.RoleRepository;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginForm form) {
        User user = userRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("login success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Username or password not valid", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterForm form){

        if (userRepository.existsByEmail(form.getEmail())){
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(form.getUsername())){
            return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                form.getName(),
                form.getEmail(),
                form.getUsername(),
                passwordEncoder.encode(form.getPassword())
        );

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role tidak ada"));
        user.setRole(role);
        user.setEnabled(true);

        userRepository.save(user);

        return new ResponseEntity<>("Registration success", HttpStatus.OK);
    }

}
