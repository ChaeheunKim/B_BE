package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.example.config.RedisUtils;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.ResponseEntityProvider;
import org.example.repository.UserRepository;
import org.example.service.SendvericationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller
public class VercationController {

    @Autowired
    private SendvericationCodeService sendVerficationcode;
    private UserRepository userRepository;
    private ResponseEntityProvider responseEntityProvider;



    @PostMapping("api/user/sendemail")
    public ResponseEntity<?> sendmail(@RequestBody @Valid UserRequestDTO.SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
        sendVerficationcode.sendMail(sendEmailRequestDTO);
        return responseEntityProvider.successWithoutData("인증 코드 전송 성공했습니다.");
    }


    @PostMapping("api/user/authentication")
    public ResponseEntity<?> ConfirmCode(@RequestBody @Valid UserRequestDTO.VerficationRequestDTO verficationRequestDTO)  {
        sendVerficationcode.VerficationEmail(verficationRequestDTO);
        UserResponseDTO.VerficationResponseDTO verficationResponseDTO=new UserResponseDTO.VerficationResponseDTO(verficationRequestDTO.getEmail());
        return responseEntityProvider.successWithData("인증 성공했습니다.", verficationResponseDTO.getEmail());
    }





}