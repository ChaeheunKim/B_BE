package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.dto.PostResponseDTO;
import org.example.entity.Post;
import org.example.repository.ImageRepository;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    /*
    * Post 등록
    * @param PostRequestDTO
    * @return postId
    * */
    public int createPost(PostRequestDTO requestDTO, List<MultipartFile> images){

//        DTO를 엔티티로 매핑 - activity 추가

//        int img_thumbnail = requestDTO.getImg_thumbnail();

        Post post = requestDTO.toEntity(requestDTO);

        postRepository.save(post);

        // 사진 저장 메소드 실행

        return post.getPostId();
    }

    public List<PostResponseDTO> getPostList(PostRequestDTO requestDTO){
        List<Post> posts = postRepository.findByCategory(requestDTO.getCategory());
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostResponseDTO convertToDTO(Post post){
        // Entity를 DTO로 변환
        PostResponseDTO dto = new PostResponseDTO();

        return dto;
    }

    public int deletePost(PostRequestDTO requestDTO){
        // 게시글 조회 및 예외 처리

            Post target = postRepository.findById(requestDTO.getPost_id())
                    .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패! 해당 게시글이 없습니다."));

            // 게시글 삭제
            postRepository.delete(target);

            // 게시글 번호 반환
            return target.getPostId();
    }

    public int updatePost(PostRequestDTO requestDTO){
        // 게시글 조회 및 예외 처리

            Post target = postRepository.findById(requestDTO.getPost_id())
                    .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패! 해당 게시글이 없습니다."));

            // 게시글 수정
            target.builder()
                    .title(requestDTO.getTitle())
                    .content(requestDTO.getContent())
                    .category(requestDTO.getCategory())
                    .participant(requestDTO.getParticipant())
                    .projectCategory(requestDTO.getProjectCategory())
                    .period(requestDTO.getPeriod())
                    .build();

            postRepository.save(target);

            // 게시글 번호 반환
            return target.getPostId();
    }


}
