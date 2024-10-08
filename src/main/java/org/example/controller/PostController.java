package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.dto.PostResponseDTO;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    /*
    * 상품 등록
    * @param PostRequestDTO
    * @return post_id, post_name*/
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@Valid @RequestParam("image")List<MultipartFile> image, @RequestBody PostRequestDTO requestDTO){
        int postId = postService.createPost(requestDTO, image);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 완료. 게시글번호 : " + postId);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostList(@Valid @RequestBody PostRequestDTO requestDTO){
        List<PostResponseDTO> posts = postService.getPostList(requestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(posts);

    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<String> deletePost(@RequestBody PostRequestDTO requestDTO){

        int postId = postService.deletePost(requestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글 삭제 완료. 게시글 번호: "+ postId);
    }

    @PatchMapping("/post/{post_id}")
    public ResponseEntity<String> updatePost(@RequestBody PostRequestDTO requestDTO){

        int postId = postService.updatePost(requestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body("게시글 수정 완료. 게시글 번호: "+ postId);
    }


}
