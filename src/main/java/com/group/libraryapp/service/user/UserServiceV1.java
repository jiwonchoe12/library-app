package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {
    private final UserJdbcRepository userJdbcRepository;

    public UserServiceV1(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public void saveUser(String name, Integer age) {
        this.userJdbcRepository.insertUser(name, age);
    }

    public List<UserResponse>  getUsers () {
        return (this.userJdbcRepository.getUsers());
    }

    public void updateUser(UserUpdateRequest request) {
        boolean isUserNotExist = userJdbcRepository.isUserNotExist(request.getId());
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }
        userJdbcRepository.updateUserName(request);
    }

    public void deleteUser(String name) {
        boolean isUserNotExist = userJdbcRepository.isUserNotExist(name);
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }
        userJdbcRepository.deleteUser(name);

    }
}
