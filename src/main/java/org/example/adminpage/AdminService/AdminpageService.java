package org.example.adminpage.AdminService;

import lombok.RequiredArgsConstructor;

import org.example.post.PostEntity.Post;
import org.example.user.UserEntity.State;
import org.example.user.UserEntity.User;
import org.example.post.PostRepository.PostRepository;
import org.example.user.UserRepository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminpageService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public List<User> getPendingUsers() {
        return userRepository.findByState(State.pending);
    }
    public List<User> getapprovedUsers() {
        return userRepository.findByState(State.approved);
    }

    public Page<Post> AdminPaging(int index){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); //최신 것부터 정렬
        Pageable pageable = PageRequest.of(index, 10, Sort.by(sorts)); //요청할 페이지 번호, 페이지당 데이터 수
        return postRepository.findAll(pageable);
    }

}
