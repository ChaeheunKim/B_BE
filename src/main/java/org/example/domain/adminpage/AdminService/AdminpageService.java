package org.example.domain.adminpage.AdminService;


import lombok.RequiredArgsConstructor;

import org.example.domain.post.DTO.PostResponseDTO;
import org.example.domain.post.Entity.*;
import org.example.domain.post.Repository.NetworkingRepository;
import org.example.domain.post.Repository.ProjectRepository;
import org.example.domain.post.Repository.SeminarRepository;
import org.example.domain.post.Repository.StudyRepository;
import org.example.domain.user.UserEntity.State;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserRepository.UserRepository;
import org.example.global.errors.ErrorCode;
import org.example.global.errors.exception.Exception404;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
@RequiredArgsConstructor
public class AdminpageService {
    private final UserRepository userRepository;
    private final NetworkingRepository networkingRepository;
    private final SeminarRepository seminarRepository;
    private final StudyRepository studyRepository;
    private final ProjectRepository projectRepository;



    public List<User> getPendingUsers() {
        return userRepository.findByState(State.pending);
    }
    public List<User> getapprovedUsers() {
        return userRepository.findByState(State.approved);
    }


    public List<PostResponseDTO.AdminPostResponseDTO> AdminPaging(int index){
        //게시글 모든 레포에서 게시글 가져와서 최신순 정렬
        List<Networking> networkingList = networkingRepository.findAll();
        List<Seminar> seminarList =seminarRepository.findAll();
        List<Study> studyList =studyRepository.findAll();
        List<Project> projectList =projectRepository.findAll();

        List<PostResponseDTO.AdminPostResponseDTO> allposts = Stream.of(networkingList,studyList,seminarList,projectList)
                .flatMap(List::stream)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        //최신순 정렬
        List<PostResponseDTO.AdminPostResponseDTO> sortedPosts = allposts.stream()
                .sorted(Comparator.comparing(PostResponseDTO.AdminPostResponseDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());


        // 페이징 처리
        Pageable pageable = PageRequest.of(index, 10);
        int end = Math.min((index-1)*10 + pageable.getPageSize(),sortedPosts.size());
        if((index-1)*10 >= sortedPosts.size()) return Collections.emptyList(); //예외처리
        List<PostResponseDTO.AdminPostResponseDTO> pagedPosts = sortedPosts.subList((index-1)*10, end);

        return pagedPosts;
    }


    //DTO 변환
    private PostResponseDTO.AdminPostResponseDTO convertToDTO(Object post) {
        // 각 엔티티 타입에 맞게 DTO로 변환하는 로직을 작성
        if (post instanceof Networking) {
            return new PostResponseDTO.AdminPostResponseDTO(((Networking) post).getId(),((Networking) post).getTitle(), ((Networking) post).getPeriod(), Category.Networking,((Networking) post).getCreatedAt());
        } else if (post instanceof Seminar) {
            return new PostResponseDTO.AdminPostResponseDTO(((Seminar) post).getId(),((Seminar) post).getTitle(), ((Seminar) post).getPeriod(), Category.Seminar, ((Seminar) post).getCreatedAt());
        } else if (post instanceof Study) {
            return new PostResponseDTO.AdminPostResponseDTO(((Study) post).getId(),((Study) post).getTitle(), ((Study) post).getPeriod(), Category.Study, ((Study) post).getCreatedAt());
        } else if (post instanceof Project) {
            return new PostResponseDTO.AdminPostResponseDTO(((Project) post).getId(),((Project) post).getTitle(), ((Project) post).getPeriod(), Category.Project, ((Project) post).getCreatedAt());
        }
        return null;
    }


}
