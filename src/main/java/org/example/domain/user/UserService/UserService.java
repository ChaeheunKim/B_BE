
package org.example.domain.user.UserService;

import lombok.RequiredArgsConstructor;
import org.example.domain.user.UserEntity.UserImage;
import org.example.domain.user.UserRepository.UserImageRepository;
import org.example.global.JWT.JwtTokenProvider;
import org.example.domain.user.UserEntity.Role;
import org.example.domain.user.UserEntity.State;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserRepository.UserRepository;
import org.example.domain.user.UserDTO.UserRequestDTO;
import org.example.domain.user.UserDTO.UserResponseDTO;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwttokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ResponseEntityProvider responseEntityProvider;
    private final UserImageRepository userImageRepository;



    //로그인 서비스
    @Transactional(readOnly = true)
    public UserResponseDTO.LoginResponseWithTokenDTO login(UserRequestDTO.LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        //토큰 발급
        String perfix = "Bearer ";
        String accesstoken = perfix + jwttokenProvider.createToken(user.getEmail(), user.getGrade());
        UserImage userImage = userImageRepository.findByUser(user);
        UserResponseDTO.LoginResponseDTO loginResponseDTO = new UserResponseDTO.LoginResponseDTO(user.getEmail(), user.getPassword(), user.getGrade(),userImage.getImg_url());

        return new UserResponseDTO.LoginResponseWithTokenDTO(loginResponseDTO, accesstoken);
    }




    //회원가입 서비스
    @Transactional
    public void signup(UserRequestDTO.signupRequestDTO requestDTO) {
        // 비밀번호 암호화
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        userRepository.save(requestDTO.toEntity(Role.USER, State.pending));

    }

    //비밀번호 변경
    @Transactional
    public ResponseEntity changepassword(String email, UserRequestDTO.changepasswordRequestDTO changepasswordRequestDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception404(null, ErrorCode.NOT_FOUND_USER));
        if (!passwordEncoder.matches(changepasswordRequestDTO.getCurrentPassword(), user.getPassword())) {
            return responseEntityProvider.FailWithoutData(ErrorCode.FAILURE_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(changepasswordRequestDTO.getNewPassword()));
        userRepository.save(user);
        return responseEntityProvider.successWithoutData("비밀번호 변경에 성공했습니다.");
    }
}