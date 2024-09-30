package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequestDTO;
import org.example.service.PostImageService;
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

    /*
    * 상품 등록
    * @param PostRequestDTO
    * @return post_id, post_name*/
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@Valid @RequestParam("image")List<MultipartFile> image, @ModelAttribute PostRequestDTO requestDTO){
        int postId = postService.createPost(requestDTO, image);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 완료. 게시글번호 : " + postId);
    }


}
