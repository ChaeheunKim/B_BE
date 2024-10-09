package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.entity.Post;
import org.example.entity.UserImage;
import org.example.repository.PostImageRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserImageRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserImageRepository userImageRepository;

    private final PostImageService postImageService;  // Spring이 관리하는 PostImageService 주입


    /**
     * Post 등록
     * @param requestDTO - 게시물 요청 데이터
     * @param images - 업로드할 이미지 파일 리스트
     * @return postId - 생성된 게시물의 ID
     */
    public boolean createPost(PostRequestDTO requestDTO, List<MultipartFile> images){

        try {
            Post post = requestDTO.toEntity(requestDTO);
            boolean imgThumbnail = requestDTO.isImgThumbnail();
            post = postRepository.save(post);


            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            postImageService.uploadPostImages(post, images, imgThumbnail);

            return true;
        } catch (Exception e) {
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }

    public boolean createUserProfile(MultipartFile image,Long user_id){

        try {
            postImageService.uploadUserImages(image,user_id);

            return true;
        } catch (Exception e) {
            // 예외 처리 로직 (로깅 등)
            return false; // 어떤 이유로든 실패하면 false 반환
        }

    }

//    public List<PostResponseDTO> getPostList(PostRequestDTO requestDTO){
//        List<Post> posts = postRepository.findByCategory(requestDTO.getCategory());
//        return posts.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }


    public int deletePost(int post_id){
        // 게시글 조회 및 예외 처리

        Post target = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패! 해당 게시글이 없습니다."));

        // 게시글 삭제
        postRepository.delete(target);

        // 게시글 번호 반환
        return target.getPostId();
    }
//
//    public int updatePost(PostRequestDTO requestDTO){
//        // 게시글 조회 및 예외 처리
//
//            Post target = postRepository.findById(requestDTO.getPost_id())
//                    .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패! 해당 게시글이 없습니다."));
//
//            // 게시글 수정
//            target.builder()
//                    .title(requestDTO.getTitle())
//                    .content(requestDTO.getContent())
//                    .category(requestDTO.getCategory())
//                    .participant(requestDTO.getParticipant())
//                    .projectCategory(requestDTO.getProjectCategory())
//                    .period(requestDTO.getPeriod())
//                    .build();
//
//            postRepository.save(target);
//
//            // 게시글 번호 반환
//            return target.getPostId();
//    }
//    private PostResponseDTO convertToDTO(Post post){
//        // Entity를 DTO로 변환
//        PostResponseDTO dto = new PostResponseDTO();
//
//        return dto;
//    }


}
