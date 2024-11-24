package org.example.domain.post.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Service.ProjectService;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ProejctController {
    private final ProjectService projectService;
    private final ResponseEntityProvider responseEntityProvider;

    //프로젝트 게시글 등록
    @PostMapping(value = "/post/project", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> createProjectPost(@RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ){

        boolean success = projectService.createProjectPost(requestDTO, image);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 생성에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 생성에 실패하였습니다");
        }
    }


    //프로젝트 게시글 리스트 조회
    @GetMapping("/post/project")
    public ResponseEntity<?> getPostList(){
        List<PostResponseDTO.ProjectItem> postResponseDTO = projectService.getProjectList();
        return responseEntityProvider.successWithData("리스트 조회에 성공했습니다.",postResponseDTO);

    }

    //프로젝트 게시글 수정
    @PatchMapping(value = "/post/project/{post_id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> ModifyPost(@Valid @PathVariable("post_id") int postId, @RequestPart(value = "post", required = true) PostRequestDTO requestDTO, @RequestPart(value = "image", required = false) List<MultipartFile> image ) throws IllegalAccessException {

        boolean success = projectService.ProjectUpdatePost(postId,requestDTO,image);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 수정에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 수정에 실패하였습니다");
        }
    }

    //프로젝트 게시글 삭제
    @DeleteMapping("/post/project/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") int post_id) {

        boolean success = projectService.deleteProjectPost(post_id);

        if (success) {
            return responseEntityProvider.successWithoutData("게시글 삭제에 성공했습니다.");
        } else {
            return responseEntityProvider.FailWithoutData("게시글 삭제에 실패하였습니다");
        }

    }
}
