package com.yudystriawan.complaintserver.services;

import com.yudystriawan.complaintserver.models.CustomUserDetails;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username or Password is wrong"));

        UserDetails userDetails = new CustomUserDetails(user.get());

        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }
}
