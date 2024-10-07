package org.example.service;

import lombok.RequiredArgsConstructor;

import org.example.entity.State;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminpageService {
    private final UserRepository userRepository;


    public List<User> getPendingUsers() {
        return userRepository.findByApprovalStatus(State.pending);
    }
}
