package org.example.domain.post.Service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.ProjectImageRepository;
import org.example.domain.post.Repository.ProjectRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception400;
import org.example.global.errors.exception.Exception404;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final PostImageService postImageService;
    private final ProjectImage projectImage;
    private final Project project;
    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;
    private final PostService postService;

    //프로젝트 게시글 등록
    public  boolean createProjectPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            boolean imgThumbnail = requestDTO.isImgThumbnail();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<Object> imageEntities = postImageService.uploadPostImages(projectImage, images, imgThumbnail);
            project.toEntity(requestDTO, imageEntities);
            projectRepository.save(project);
            return true;
        }
        catch(Exception e){
            throw new Exception400(null, ErrorCode.UNSUPPORTED_MEDIA_TYPE);

        }

    }

    //프로젝트 게시글 수정
    public  boolean ProjectUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images) throws IllegalAccessException {
        boolean imgThumbnail = postRequestDTO.isImgThumbnail();
        List<Object> imageentities = postImageService.uploadPostImages(project, images, imgThumbnail);
        boolean success = false;
        try {
            Project project = projectRepository.findById(Id);
            project.update(postRequestDTO, imageentities);
            success = true;
        }
       catch(Exception e) {
            success = false;
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //프로젝트 게시글 가져오기
    public void getProjectList() {
        List<Project> projectList = projectRepository.findAll();
        List<PostResponseDTO.ProjectItem> projectItemList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectImage image = projectImageRepository.findBythumbnailTrue();
            PostResponseDTO.ProjectItem postResponseDTO = new PostResponseDTO.ProjectItem(project.getTitle(), image.getUrl(), project.getProjectCategory());
            projectItemList.add(postResponseDTO);
        }

    }

    //프로젝트 게시글 삭제
    //게시글 삭제
    public boolean deleteProjectPost(int Id) {
        boolean success = false;
        try {

                Project project = projectRepository.findById(Id);
                projectRepository.delete(project);
                List<ProjectImage> images = projectImageRepository.findByProjectId(Id);
                postService.deleteImagesFromS3(images);
                projectImageRepository.deleteAll(images);
            return true;


        } catch (Exception e) {
                throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
            }
        }

}
