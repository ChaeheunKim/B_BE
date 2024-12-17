package org.example.domain.post.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostDetailResponseDTO;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Service.StudyService;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class StudyController {

    private final StudyService studyService;
    private final ResponseEntityProvider responseEntityProvider;

    //스터디 게시글 등록
    @PostMapping(value = "/post/study", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> createNetworkingPost(@RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ){

        boolean success = studyService.createStudyPost(requestDTO, image);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 생성에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 생성에 실패하였습니다");
        }
    }

    //스터디 게시글 리스트 조회
    @GetMapping("/post/study")
    public ResponseEntity<?> getPostList(){
        List<PostResponseDTO.StudyItem> postResponseDTO = studyService.getStudyList();
        return responseEntityProvider.successWithData("리스트 조회에 성공했습니다.",postResponseDTO);

    }

    //스터디 게시글 수정
    @PatchMapping(value = "/post/study/{post_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> ModifyPost(@Valid @PathVariable("post_id") int post_id, @RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ) throws IllegalAccessException {

        boolean success = studyService.StudyUpdatePost(post_id,requestDTO,image);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 수정에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 수정에 실패하였습니다");
        }
    }

    //스터디 게시글 삭제
    @DeleteMapping("/post/study/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") int post_id) {

        boolean success = studyService.deleteStudyPost(post_id);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 삭제에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 삭제에 실패하였습니다");
        }

    }

    //스터디 게시글 세부정보 조히
    @GetMapping("post/study/{post_id}")
    public ResponseEntity<?> detailPost(@PathVariable("post_id") int post_id){
        PostDetailResponseDTO postDetailResponseDTO =studyService.detailStudyPost(post_id);
        return responseEntityProvider.successWithData("게시글 세부정보 조회에 성공했습니다.",postDetailResponseDTO);
    }


}
