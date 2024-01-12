package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import com.group.libraryapp.domain.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional //아래 있는 함수가 시작될 때 start transaction;을 해준, 함수가 예외없이 잘 끝나면 commit, 아니면 rollback
    public void saveUser(UserCreateRequest request) {
        // save 메소드에 객체를 넣어주면 INSERT SQL이 자동으로 날라간다
        User u = userRepository.save(new User(request.getName(), request.getAge()));
        //u.getId();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll(); //해당 테이블에 있는 모든 데이터를 가져온다
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        //findById()의 결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalAccessError::new); //orElseThrow() : Optional의 인자가 null일 경우 예외처리
        user.updateName(request.getName()); //User클래스안에 있는 우리가 만든 함수, 객체를 변경해준다
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }
}
