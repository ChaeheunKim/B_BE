package org.example.user.UserController;

import org.example.user.UserDTO.PasswordRequestDTO;
import org.example.user.UserDTO.PasswordResponseDTO;
import org.example.user.UserService.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/password")  // 비밀번호 변경 관련 API 경로 설정
public class PasswordController {

    @Autowired
    private PasswordService passwordService;  // PasswordService 주입

    /**
     * 비밀번호 변경 요청
     * @param userId URL 경로에서 사용자 ID를 받아옴
     * @param requestDTO 현재 비밀번호와 새 비밀번호가 포함된 요청 데이터
     * @return 비밀번호 변경 결과를 포함한 응답 객체
     */
    @PostMapping("/{user_id}")
    public ResponseEntity<PasswordResponseDTO> changePassword(
            @PathVariable("user_id") Long userId,  // URL 경로에서 사용자 ID 추출
            @RequestBody PasswordRequestDTO requestDTO) {  // 요청 바디로부터 비밀번호 정보 추출
        PasswordResponseDTO responseDTO = passwordService.changePassword(userId, requestDTO);
        return ResponseEntity.ok(responseDTO);  // 성공 응답 반환
    }
}
