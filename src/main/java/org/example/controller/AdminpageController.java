
package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.entity.User;
import org.example.service.AdminpageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AdminpageController {
    private final AdminpageService adminpageService;
    private final ResponseEntityProvider responseEntityProvider;

    @GetMapping("/user/state")
    public ResponseEntity AdminPage(){
           List<User> waitingUsers = adminpageService.getPendingUsers();
        List<UserResponseDTO.adminWaitingResponseDTO> adminWaitingResponseDTOList = waitingUsers.stream()
                .map(user -> new UserResponseDTO.adminWaitingResponseDTO(user.getName(), user.getEmail(),user.getCreatedAt()))
                .collect(Collectors.toList()); // 리스트로 수집
         return responseEntityProvider.successWithData("성공적으로 데이터를 불러왔습니다.",adminWaitingResponseDTOList);
    }

}
