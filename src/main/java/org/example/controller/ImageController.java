package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.ResponseEntityProvider;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/imageAdd") // API 경로 설정
public class ImageController {
    private final PostService postService;
    private final ResponseEntityProvider responseEntityProvider;


    /**
     * 사용자 이미지 업로드
     *
     * @param userId URL 경로에서 사용자 ID를 받아옴
     * @param image  요청으로 받은 이미지 파일 (multipart/form-data 형식)
     * @return 업로드 결과를 포함한 응답 객체
     */

    @PostMapping(value = "/{user_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImage(
            @PathVariable("user_id") Long userId,
            @RequestPart(value = "image") MultipartFile image) {
        boolean success = postService.createUserProfile(image,userId);
        if (success) {
            return responseEntityProvider.successWithoutData("이미지 업로드 되었습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("이미지 업로드 실패하였습니다.");
        } // 성공 응답과 데이터를 함께 반환
    }
}
