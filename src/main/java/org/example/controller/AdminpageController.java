
package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.entity.User;
import org.example.service.AdminpageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AdminpageController {
    private final AdminpageService adminpageService;
    private final ResponseEntityProvider responseEntityProvider;

    @GetMapping("/admin/state")
    public ResponseEntity AdminPage(){
           List<User> waitingUsers = adminpageService.getPendingUsers();
           List<User> approvedUsers = adminpageService.getapprovedUsers();
        List<UserResponseDTO.adminWaitingResponseDTO> adminWaitingResponseDTOList = waitingUsers.stream()
                .map(user -> new UserResponseDTO.adminWaitingResponseDTO( user.getEmail(),user.getName(),user.getCreatedAt()))
                .collect(Collectors.toList()); // 리스트로 수집
        List<UserResponseDTO.adminmemberResponseDTO> adminmemberResponseDTOList = approvedUsers.stream()
                .map(user -> new UserResponseDTO.adminmemberResponseDTO(user.getEmail(),user.getName(), user.getCardinal(), user.getPart()))
                .collect(Collectors.toList()); // 리스트로 수집
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("PendingList", adminWaitingResponseDTOList);
        responseMap.put("ApprovedList", adminmemberResponseDTOList);

        // Return response with data
        return responseEntityProvider.successWithData("성공적으로 데이터를 불러왔습니다.", responseMap);
    }

}
