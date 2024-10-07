package org.example.controller;

import org.example.dto.ImageResponseDTO;
import org.example.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/imageAdd") // API 경로 설정
public class ImageController {

    @Autowired
    private ImageService imageService;  // ImageService를 주입받아 사용

    /**
     * 사용자 이미지 업로드
     * @param userId URL 경로에서 사용자 ID를 받아옴
     * @param image 요청으로 받은 이미지 파일 (multipart/form-data 형식)
     * @return 업로드 결과를 포함한 응답 객체
     */

    @PostMapping("/{user_id}")
    public ResponseEntity<ImageResponseDTO> uploadImage(
            @PathVariable("user_id") Long userId,  // URL 경로에서 사용자 ID 추출
            @RequestPart("image") MultipartFile image) {  // multipart 형식으로 이미지를 받음
        ImageResponseDTO responseDTO = imageService.uploadImage(userId, image);  // 이미지 업로드 서비스 호출
        return ResponseEntity.ok(responseDTO);  // 성공 응답 반환
    }
}
