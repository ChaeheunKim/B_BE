package org.example.user.UserService;

import org.example.user.UserDTO.PasswordRequestDTO;
import org.example.user.UserDTO.PasswordResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public PasswordResponseDTO changePassword(Long userId, PasswordRequestDTO requestDTO) {
        PasswordResponseDTO responseDTO = new PasswordResponseDTO();

        // 새 비밀번호 유효성 검사 (8자 이상)
        if (requestDTO.getNewpassword().length() < 8) {
            responseDTO.setStatus("error");
            responseDTO.setError("8자 이상을 입력하세요.");
            return responseDTO;
        }

        // 기존 비밀번호와 새 비밀번호 검증 로직
        responseDTO.setStatus("success");
        responseDTO.setResponse(null);
        responseDTO.setError(null);

        return responseDTO;
    }
}
