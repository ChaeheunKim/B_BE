package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.entity.State;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserController {
    private final UserService userService;
    private final ResponseEntityProvider responseEntityProvider;

    //로그인 api
    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO.LoginRequestDTO loginrequestDTO, HttpServletResponse http){
        UserResponseDTO.LoginResponseWithTokenDTO loginResponseWithTokenDTO = userService.login(loginrequestDTO);
        String accessToken = loginResponseWithTokenDTO.getToken();
        // jwt 헤더에 담기
        http.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        return responseEntityProvider.successWithData("로그인에 성공했습니다.",loginResponseWithTokenDTO.getLoginResponseDTO());
    }

    //회원가입 api
    @PostMapping("/user/signup")
    public ResponseEntity signup(@RequestBody @Valid UserRequestDTO.signupRequestDTO signuprequestDTO, HttpServletResponse http){
            userService.signup(signuprequestDTO);
            signuprequestDTO.setState(State.pending);
            return responseEntityProvider.successWithData("회원가입 요청에 성공했습니다.",signuprequestDTO);
        }

}
