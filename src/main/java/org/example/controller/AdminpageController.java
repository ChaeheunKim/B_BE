
/*package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequestDTO;
import org.example.entity.State;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AdminpageController {

    @GetMapping("/user/state/{email}")
    public ResponseEntity AdminPage(@RequestParam ){
        userService.signup(signuprequestDTO);
        signuprequestDTO.setState(State.pending);
        return responseEntityProvider.successWithData("회원가입 요청에 성공했습니다.",signuprequestDTO);
    }

}
*/