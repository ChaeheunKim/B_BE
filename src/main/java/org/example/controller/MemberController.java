package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.MemberResponseDTO;
import org.example.entity.User;
import org.example.service.AdminpageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AdminpageService adminpageService;

    @GetMapping("api/member")
    public ResponseEntity MemberList(){
        List<User> approveUsers = adminpageService.getapprovedUsers();
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(approveUsers)
    }

    
}
개발 중

 */
