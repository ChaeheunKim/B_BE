package org.example.domain.post.Service;

import lombok.RequiredArgsConstructor;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
;
import java.util.*;



@Service
@RequiredArgsConstructor
public class PostImageService {

    private final ProjectImageRepository projectImageRepository;
    private final SeminarImageRepository seminarImageRepository;
    private final NetworkingImageRepository networkingImageRepository;
    private final StudyImageRepository studyImageRepository;
    private final ProjectRepository projectRepository;
    private final SeminarRepository seminarRepository;
    private final StudyRepository studyRepository;
    private final NetworkingRepository networkingRepository;
    private final CommonService commonService;



    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    //프로젝트 이미지 업로드
    public List<ProjectImage> uploadProjectImages(int project_id, List<MultipartFile> images, int imgThumbnail_id){
        List<ProjectImage> createdEntities = new ArrayList<>();
        //썸네일 이미지 구분 변수
        int index = 1;

        // project_id를 통해 Project 엔티티를 조회
        Optional<Project> project = projectRepository.findById(project_id);

        for (MultipartFile image : images) {
            String s3ImageUrl = commonService.saveImageToS3(image);
            String imgName = image.getOriginalFilename();

            // imgThumbnail_id와 index를 비교하여 썸네일 여부 결정
            boolean imgThumbnail = (index == imgThumbnail_id);
            ProjectImage projectImageEntity = new ProjectImage(imgName, s3ImageUrl, imgThumbnail, project.get());
            projectImageRepository.save(projectImageEntity);
            createdEntities.add(projectImageEntity);

            index++;
        }
        return createdEntities;

    }

    //세미나 이미지 업로드
    public List<SeminarImage> uploadSeminarImages(int seminar_id,List<MultipartFile> images, int imgThumbnail_id){
        List<SeminarImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Seminar> seminar = seminarRepository.findById(seminar_id);
        for (MultipartFile image : images) {
            String s3ImageUrl = commonService.saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            SeminarImage SeminarImageEntity=new SeminarImage(imgName,s3ImageUrl,imgThumbnail,seminar.get());
            seminarImageRepository.save(SeminarImageEntity);
            createdEntities.add(SeminarImageEntity);

            index++;
        }
        return createdEntities;

    }

    //스터디 이미지 업로드
    public List<StudyImage> uploadStudyImages(int study_id,List<MultipartFile> images, int imgThumbnail_id){
        List<StudyImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Study> study=studyRepository.findById(study_id);
        for (MultipartFile image : images) {
            String s3ImageUrl = commonService.saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            StudyImage StudyImageEntity=new StudyImage(imgName,s3ImageUrl,imgThumbnail,study.get());
            studyImageRepository.save(StudyImageEntity);
            createdEntities.add(StudyImageEntity);

            index++;
        }
        return createdEntities;

    }

    //네트워킹 이미지 업로드
    public List<NetworkingImage> uploadNetworkingImages(int networking_id,List<MultipartFile> images, int imgThumbnail_id){
        List<NetworkingImage> createdEntities = new ArrayList<>();
        int index = 1;
        Optional<Networking> networking=networkingRepository.findById(networking_id);

        for (MultipartFile image : images) {
            String s3ImageUrl = commonService.saveImageToS3(image);
            String imgName = image.getOriginalFilename();
            boolean imgThumbnail = (index == imgThumbnail_id);

            NetworkingImage NetworkingImageEntity = new NetworkingImage(imgName,s3ImageUrl,imgThumbnail,networking.get());
            networkingImageRepository.save(NetworkingImageEntity);
            createdEntities.add(NetworkingImageEntity);

            index++;
        }
        return createdEntities;

    }




}
