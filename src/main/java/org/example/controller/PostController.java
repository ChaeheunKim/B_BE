package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostDetailResponseDTO;
import org.example.dto.PostRequestDTO;
import org.example.dto.PostResponseDTO;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.entity.PostImage;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    /*
    * 게시글 등록
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

    @GetMapping("/post")
    public ResponseEntity<?> getPostList(@Valid @RequestParam("category") String requestCategory){ // 스프링 부트 3.2부터 매개변수의 이름을 인식하지 못하는 문제가 있다고 한다. -parameters 옵션 부여 필요
        Category category = Category.valueOf(requestCategory);
        PostResponseDTO response = postService.getPostList(category);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<?> getPostDetail(@Valid @PathVariable("post_id") int postId){
        // 1. 받은 post_id로 해당 post를 찾은 다음 모든 값을 되돌려주려고함.
        Post post = postService.getPostDetail(postId);
        // 2. post_id를 FK로 join해서 모든 Image를 꺼내 Url List로 만듬.

        PostDetailResponseDTO detailResponse = postService.getdetailImage(postId, post);

        return ResponseEntity.status(HttpStatus.CREATED).body(detailResponse);
    }
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
