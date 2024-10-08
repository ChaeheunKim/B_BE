package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.entity.State;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserController {
    private final UserService userService;
    private final ResponseEntityProvider responseEntityProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //로그인 api
    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO.LoginRequestDTO loginrequestDTO, HttpServletResponse http){
        UserResponseDTO.LoginResponseWithTokenDTO loginResponseWithTokenDTO = userService.login(loginrequestDTO);
        String accessToken = loginResponseWithTokenDTO.getToken();
        Optional<User> optionalUser = userRepository.findByEmail(loginrequestDTO.getEmail());
        if(optionalUser.isEmpty()){
           return responseEntityProvider.FailWithoutData("회원가입을 해주세요");
        }
        User user = optionalUser.get();


        if (!passwordEncoder.matches(loginrequestDTO.getPassword(), user.getPassword())) {
            return responseEntityProvider.FailWithoutData("아이디와 비밀번호가 일치하지 않습니다.");
        }
        if(user.getState().equals(State.pending) | user.getState().equals(State.rejected)) {
            return responseEntityProvider.FailWithoutData("계정이 승인되지 않았습니다.");
        }
        // jwt 헤더에 담기
        http.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        return responseEntityProvider.successWithData("로그인에 성공했습니다.",loginResponseWithTokenDTO.getLoginResponseDTO().getEmail());
    }

    //회원가입 api
    @PostMapping("/user/signup")
    public ResponseEntity signup(@RequestBody @Valid UserRequestDTO.signupRequestDTO signuprequestDTO, HttpServletResponse http){
            userService.signup(signuprequestDTO);
            signuprequestDTO.setState(State.pending);
            return responseEntityProvider.successWithoutData("회원가입 요청에 성공했습니다.");
        }

}
