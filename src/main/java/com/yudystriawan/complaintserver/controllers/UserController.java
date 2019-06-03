package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.exceptions.UserNotFoundException;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(params = "username")
    public User getOne(@Param("username") String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
