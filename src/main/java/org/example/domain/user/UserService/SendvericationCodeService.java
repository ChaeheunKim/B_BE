package org.example.domain.user.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.global.config.RedisUtils;
import org.example.domain.user.UserDTO.UserRequestDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;
@RequiredArgsConstructor
@Service
public class SendvericationCodeService {


        private final JavaMailSender javaMailSender;
        private final RedisUtils redisUtils;

        // 인증번호 이메일 보내기
        public void sendMail (UserRequestDTO.SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
            String email = sendEmailRequestDTO.getEmail();

            // 코드 생성
                String code = generateCode();
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setFrom("alzl7410@gmail.com"); //보내는사람
                helper.setTo(email); //받는사람
                helper.setSubject("이메일 인증 번호 입니다"); //메일제목
                helper.setText("5분 안에 입력해주세요\n"+ "이메일 인증코드: " + code);

                // Redis에 인증 코드 저장 (5분 동안 유지)
                redisUtils.setDataExpire(email, code, 60 * 5L);
                javaMailSender.send(mimeMessage);

            }


        public  void VerficationEmail(UserRequestDTO.VerficationRequestDTO verficationRequestDTO){
            String email = verficationRequestDTO.getEmail();
            String Usercode = verficationRequestDTO.getUsercode();
            if(!Usercode.equals(redisUtils.getData(email))){
                throw new IllegalArgumentException("일치하는 사용자가 없습니다");
            }
        }


        private String generateCode() {
            // 랜덤 인증번호 생성
            return UUID.randomUUID().toString().substring(0, 6);
        }
    }


