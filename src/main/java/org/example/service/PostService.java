package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.entity.Activity;
import org.example.entity.Post;
import org.example.repository.ActivityRepository;
import org.example.repository.ImageRepository;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ActivityRepository actiRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    /*
    * Post 등록
    * @param PostRequestDTO
    * @return postId
    * */
    public int createPost(PostRequestDTO requestDTO, List<MultipartFile> images){

//        DTO를 엔티티로 매핑 - activity 추가
        Activity activity = Activity.builder()
                .acti_category(requestDTO.getCategory())
                .acti_participant(requestDTO.getParticipant())
                .acti_period(requestDTO.getPeriod())
                .acti_isProject(requestDTO.getIsProject())
                .acti_url(requestDTO.getUrl())
                .build();

        Activity saveEntity = actiRepository.save(activity);
        Activity res_acti = saveEntity;
        int img_thumbnail = requestDTO.getImg_thumbnail();

        Post post = Post.builder()
                .user_id(requestDTO.getUser_id())
                .activity(res_acti)
                .post_title(requestDTO.getTitle())
                .post_intro(requestDTO.getIntro())
                .post_content(requestDTO.getContent())
                .build();

        postRepository.save(post);

        // 사진 저장 메소드 실행
        PostImageService postImageService = new PostImageService(imageRepository,postRepository);
        postImageService.uploadImages(post, images, img_thumbnail);
        return post.getPost_id();
    }
}
