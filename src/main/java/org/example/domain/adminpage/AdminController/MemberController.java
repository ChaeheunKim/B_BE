package org.example.domain.adminpage.AdminController;


import lombok.RequiredArgsConstructor;
import org.example.domain.adminpage.AdminDTO.MemberResponseDTO;
import org.example.global.response.ResponseEntityProvider;
import org.example.domain.adminpage.AdminService.AdminpageService;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserEntity.UserImage;
import org.example.domain.user.UserRepository.UserImageRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

