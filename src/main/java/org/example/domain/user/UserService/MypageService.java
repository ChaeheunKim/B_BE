package org.example.domain.user.UserService;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.Service.CommonService;
import org.example.domain.user.UserDTO.MypageReqeustDTO;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserEntity.UserImage;
import org.example.domain.user.UserRepository.UserImageRepository;
import org.example.domain.user.UserRepository.UserRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@RequiredArgsConstructor
@Service
public class MypageService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final CommonService commonService;

    //유저이미지 등록
    public boolean createUserProfile(MultipartFile image, Long user_id) {

        try {User user = userRepository.findById(user_id);

            // 이미지 파일을 S3에 저장하고 URL 반환
            String s3ImageUrl = commonService.saveImageToS3(image);
            // 이미지 메타데이터 설정
            String imgName = image.getName();
            // Image 엔티티 생성 및 저장
            UserImage userImage = new UserImage(user, imgName,s3ImageUrl);
            userImageRepository.save(userImage);

            return true;
        } catch (Exception e) {
            // 예외 처리 로직 (로깅 등)
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }


    //마이페이지 세부정보 수정
    public  boolean MypageUpdate(Long Id, MypageReqeustDTO mypageRequestDTO){
        try {
            User user = userRepository.findById(Id);
            user.update(mypageRequestDTO);
            userRepository.save(user);
            return true;
        }
        catch(Exception e) {
            throw new Exception404(null, ErrorCode.NOT_FOUND_USER);
        }

    }

    //마이페이지 정보 조회
    public MypageReqeustDTO Mypage(Long Id){
            User user = userRepository.findById(Id);
            MypageReqeustDTO mypageReqeustDTO = new MypageReqeustDTO(user.getName(),
                    user.getEmail(),
                    user.getCardinal(),
                    user.getPart());
           return mypageReqeustDTO;


    }
}
