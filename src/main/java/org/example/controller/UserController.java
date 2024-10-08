/*
package org.example.controller;

import org.example.dto.UserDetailRequestDTO;
import org.example.dto.UserDetailResponseDTO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 사용자 세부 정보 수정
     * @param userId URL 경로에서 사용자 ID를 받아옴
     * @param image 프로필 이미지 파일 (multipart/form-data 형식)
     * @param requestDTO 수정할 세부 정보가 포함된 DTO
     * @return 수정 결과를 포함한 응답 객체
     */

    /**
     * 사용자 이미지 삭제
     * @param userId URL 경로에서 사용자 ID를 받아옴
     * @return 삭제 결과를 포함한 응답 객체
     */

    /**
    @PostMapping("/{user_id}")
    public ResponseEntity<UserDetailResponseDTO> updateUserDetails(
            @PathVariable("user_id") Long userId,
            @RequestPart("image") MultipartFile image,
            @ModelAttribute UserDetailRequestDTO requestDTO) {  // @ModelAttribute로 변경
        UserDetailResponseDTO responseDTO = userService.updateUserDetails(userId, image, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/image/{user_id}")
    public ResponseEntity<UserDetailResponseDTO> deleteUserImage(@PathVariable("user_id") Long userId) {
        UserDetailResponseDTO responseDTO = userService.deleteUserImage(userId);
        return ResponseEntity.ok(responseDTO);  // 성공 응답 반환
    }
}
*/