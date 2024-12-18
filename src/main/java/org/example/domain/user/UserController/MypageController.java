package org.example.domain.user.UserController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.UserDTO.MypageReqeustDTO;
import org.example.domain.user.UserService.MypageService;
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
@RequestMapping("/api") // API 경로 설정
public class MypageController {
    private final ResponseEntityProvider responseEntityProvider;
    private final MypageService mypageService;



    //유저 이미지 등록
    @PostMapping(value = "/user/imageAdd/{user_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImage(
            @PathVariable("user_id") Long userId,
            @RequestPart(value = "image") MultipartFile image) {
        boolean success = mypageService.createUserProfile(image,userId);
        if (success) {
            return responseEntityProvider.successWithoutData("이미지 업로드 되었습니다.");
        } else {
            return new Exception500(null, ErrorCode.UPLOAD_FILE_ERROR).body();
        }
    }

    //마이페이지 세부정보 수정
    @PatchMapping(value = "user/{user_id}")
    public ResponseEntity<?> updateMypage(@RequestBody @Valid MypageReqeustDTO mypageRequestDTO,
            @PathVariable("user_id") Long userId) {
        boolean success = mypageService.MypageUpdate(userId,mypageRequestDTO);
        if (success) {
            return responseEntityProvider.successWithoutData("수정 완료 했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData(ErrorCode.NOT_FOUND_USER);
        }
    }

    //마이페이지 정보 조회
    @GetMapping(value = "user/mypage/{user_id}")
    public ResponseEntity<?> Mypage(@PathVariable("user_id") Long userId){
        MypageReqeustDTO mypageReqeustDTO = mypageService.Mypage(userId);
        return responseEntityProvider.successWithData("조회에 성공했습니다.",mypageReqeustDTO);
    }
}
