package org.example.domain.post.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostDetailResponseDTO;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Service.SeminarService;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SeminarController {

        private final SeminarService seminarService;
        private final ResponseEntityProvider responseEntityProvider;

        //세미나 게시글 등록
        @PostMapping(value = "/post/seminar", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<String> createProjectPost(@RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ){

            boolean success = seminarService.createSeminarPost(requestDTO, image);

            if (success) {
                return responseEntityProvider.successWithoutData("게시글 생성에 성공했습니다.");
            } else {
                return responseEntityProvider.FailWithoutData("게시글 생성에 실패하였습니다");
            }
        }


        //세미나 게시글 리스트 조회
        @GetMapping("/post/seminar")
        public ResponseEntity<?> getPostList(){
            List<PostResponseDTO.NetworkingandSeminarItem> postResponseDTO = seminarService.getSeminarList();
            return responseEntityProvider.successWithData("리스트 조회에 성공했습니다.",postResponseDTO);

        }

        //세미나 게시글 수정
        @PatchMapping(value = "/post/seminar/{post_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<String> ModifyPost(@Valid @PathVariable("post_id") int post_id, @RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ) throws IllegalAccessException {

            boolean success = seminarService.SeminarUpdatePost(post_id,requestDTO,image);

            if (success) {
                return responseEntityProvider.successWithoutData("게시글 수정에 성공했습니다.");
            } else {
                return responseEntityProvider.FailWithoutData("게시글 수정에 실패하였습니다");
            }
        }

        //세미나 게시글 삭제
        @DeleteMapping("/post/seminar/{post_id}")
        public ResponseEntity<?> deletePost(@PathVariable("post_id") int post_id) {

            boolean success = seminarService.deleteSeminarPost(post_id);

            if (success) {
                return responseEntityProvider.successWithoutData("게시글 삭제에 성공했습니다.");
            } else {
                return responseEntityProvider.FailWithoutData("게시글 삭제에 실패하였습니다");
            }

        }

    //세미나 게시글 세부정보 조히
    @GetMapping("post/seminar/{post_id}")
    public ResponseEntity<?> detailPost(@PathVariable("post_id") int post_id){
        PostDetailResponseDTO postDetailResponseDTO = seminarService.detailSeminarPost(post_id);
        return responseEntityProvider.successWithData("게시글 세부정보 조회에 성공했습니다.",postDetailResponseDTO);
    }
}

