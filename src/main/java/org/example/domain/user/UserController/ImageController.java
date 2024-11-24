package org.example.domain.user.UserController;

import lombok.RequiredArgsConstructor;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception500;
import org.example.global.response.ResponseEntityProvider;
import org.example.domain.post.Service.CommonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/imageAdd") // API 경로 설정
public class ImageController {
    private final CommonService commonService;
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
        boolean success = commonService.createUserProfile(image,userId);
        if (success) {
            return responseEntityProvider.successWithoutData("이미지 업로드 되었습니다.");
        } else {
            return new Exception500(null, ErrorCode.UPLOAD_FILE_ERROR).body();
        }
    }
}
