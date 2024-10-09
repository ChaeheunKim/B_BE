
package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.JWT.JwtTokenProvider;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.Role;
import org.example.entity.State;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwttokenProvider;
    private final PasswordEncoder passwordEncoder;



    //로그인 서비스
    @Transactional(readOnly = true)
    public UserResponseDTO.LoginResponseWithTokenDTO login(UserRequestDTO.LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));


        String perfix = "Bearer ";

        String token = perfix + jwttokenProvider.createToken(user.getEmail(),user.getGrade());

        UserResponseDTO.LoginResponseDTO loginResponseDTO = new UserResponseDTO.LoginResponseDTO(user.getEmail(),user.getPassword());

        return new UserResponseDTO.LoginResponseWithTokenDTO(loginResponseDTO, token);
    }


    //회원가입 서비스
    @Transactional
    public void signup(UserRequestDTO.signupRequestDTO requestDTO) {
        // 비밀번호 암호화
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        userRepository.save(requestDTO.toEntity(Role.USER, State.pending));

    }
}
