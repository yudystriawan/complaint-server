package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.jwt.JwtTokenProvider;
import com.yudystriawan.complaintserver.models.response.JwtTokenResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?>login(@Valid @RequestBody LoginForm form){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getUsername(),
                        form.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String>register(@Valid @RequestBody RegisterForm form){
        if (userRepository.existsByUsername(form.getUsername())){
            return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(form.getEmail())){
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }

        //create account
        User user = new User(
                form.getName(),
                form.getEmail(),
                form.getUsername(),
                passwordEncoder.encode(form.getPassword())
        );

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

}
