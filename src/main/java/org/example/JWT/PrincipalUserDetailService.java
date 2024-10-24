package org.example.JWT;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.user.UserEntity.User;
import org.example.user.UserRepository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
@RequiredArgsConstructor
@Service
@Transactional
public class PrincipalUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        return new PrincipalUserDetails(user);

    }
}
