package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.dto.PostResponseDTO;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> createPost(@RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ){
        boolean success = postService.createPost(requestDTO, image);

        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 생성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 생성에 실패했습니다.");
        }
    }

//    @GetMapping("/post")
//    public ResponseEntity<?> getPostList(@Valid @RequestBody PostRequestDTO requestDTO){
//        List<PostResponseDTO> posts = postService.getPostList(requestDTO);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(posts);
//
//    }
//
//    @DeleteMapping("/post/{post_id}")
//    public ResponseEntity<String> deletePost(@PathVariable int post_id){
//
//        boolean success = postService.deletePost(post_id);
//
//
//        if (success) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 생성되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 생성에 실패했습니다.");
//        }
//    }
//
//    @PatchMapping("/post/{post_id}")
//    public ResponseEntity<String> updatePost(@RequestBody PostRequestDTO requestDTO){
//
//        int postId = postService.updatePost(requestDTO);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("게시글 수정 완료. 게시글 번호: "+ postId);
//    }


}
