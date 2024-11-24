package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.DTO.PostRequestDTO;
import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.ProjectImageRepository;
import org.example.domain.post.Repository.ProjectRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final PostImageService postImageService;
    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;
    private final CommonService commonService;

    //프로젝트 게시글 등록
    public  boolean createProjectPost(PostRequestDTO requestDTO, List<MultipartFile> images) {

        try {
            Project project1 = new Project(requestDTO);
            projectRepository.save(project1);
            int imgThumbnail_id = requestDTO.getImgThumbnail_id();
            // Spring이 주입한 PostImageService를 사용해 이미지 저장
            List<ProjectImage> imageEntities = postImageService.uploadProjectImages(project1.getId(), images, imgThumbnail_id);
            project1.setImages(imageEntities);
            projectRepository.save(project1);
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);

        }

    }

    //프로젝트 게시글 수정
    public  boolean ProjectUpdatePost(int Id, PostRequestDTO postRequestDTO, List<MultipartFile> images) throws IllegalAccessException {
        int imgThumbnail_id = postRequestDTO.getImgThumbnail_id();
        List<ProjectImage> imageentities = postImageService.uploadProjectImages(Id,images, imgThumbnail_id);
        boolean success = false;
        try {
            Optional<Project> project = projectRepository.findById(Id);
            project.get().update(postRequestDTO, imageentities);
            projectRepository.save(project.get());
            success = true;
        }
       catch(Exception e) {
            success = false;
            throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
        }

        return success;
    }

    //프로젝트 게시글 조회
    public List<PostResponseDTO.ProjectItem> getProjectList() {
        // 프로젝트 목록을 가져오고 썸네일 이미지가 있는 프로젝트만 필터링
        return projectRepository.findAll().stream()
                .flatMap(project ->
                        projectImageRepository.findByProject(project).stream()
                                .filter(ProjectImage::isThumbnail)
                                .map(projectImage -> new PostResponseDTO.ProjectItem(
                                        project.getTitle(),
                                        projectImage.getUrl(),
                                        project.getProjectCategory()
                                ))
                )
                .collect(Collectors.toList());
    }

    //프로젝트 게시글 삭제
    public boolean deleteProjectPost(int Id) {
        boolean success = false;
        try {

                Optional<Project> project = projectRepository.findById(Id);
                projectRepository.delete(project.get());
                List<ProjectImage> images = projectImageRepository.findByProject(project.get());
                commonService.deleteImagesFromS3(images);
                projectImageRepository.deleteAll(images);
            return true;


        } catch (Exception e) {
                throw new Exception404(null, ErrorCode.NOT_FOUND_POST);
            }
        }

}
