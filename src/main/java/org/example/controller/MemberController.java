package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.MemberResponseDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.entity.User;
import org.example.entity.UserImage;
import org.example.repository.UserImageRepository;
import org.example.service.AdminpageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AdminpageService adminpageService;
    private final ResponseEntityProvider responseEntityProvider;
    private final UserImageRepository userImageRepository;

    @GetMapping("api/user")
    public ResponseEntity MemberList(){
        List<User> approveUsers = adminpageService.getapprovedUsers();

        List<MemberResponseDTO> MemberResponseDTOList = approveUsers.stream()
                .map(user ->
                {
                    UserImage userimage =userImageRepository.findByUser(user);
                    String imageUrl = (userimage != null) ? userimage.getImg_name(): null;
                    return new MemberResponseDTO(user.getName(), user.getPart(), user.getCardinal(),imageUrl,user.getId());
                })
                .collect(Collectors.toList());

        return responseEntityProvider.successWithData("리스트를 성공적으로 불러왔습니다.",MemberResponseDTOList);
    }

    
}

