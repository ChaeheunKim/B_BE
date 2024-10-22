package org.example.user.UserController;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.user.UserDTO.UserRequestDTO;
import org.example.user.UserDTO.UserResponseDTO;
import org.example.config.response.ResponseEntityProvider;
import org.example.user.UserRepository.UserRepository;
import org.example.user.UserService.SendvericationCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RequiredArgsConstructor
@Controller
public class VercationController {

    private final SendvericationCodeService sendVerficationcode;
    private final UserRepository userRepository;
    private final ResponseEntityProvider responseEntityProvider;



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