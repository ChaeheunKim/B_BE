package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.entity.Post;
import org.example.repository.PostImageRepository;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    private final PostImageService postImageService;  // Spring이 관리하는 PostImageService 주입

    /**
     * Post 등록
     *
     * @param requestDTO - 게시물 요청 데이터
     * @param images     - 업로드할 이미지 파일 리스트
     * @return postId - 생성된 게시물의 ID
     */
    public int createPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        Post post = requestDTO.toEntity(requestDTO);
        boolean imgThumbnail = requestDTO.isImgThumbnail();
        post = postRepository.save(post);


        // Spring이 주입한 PostImageService를 사용해 이미지 저장
        postImageService.uploadPostImages(post, images, imgThumbnail);

        return post.getPostId();

    }
}