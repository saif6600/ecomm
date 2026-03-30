package com.ecomm.service.impl;

import com.ecomm.dto.UserDtos;
import com.ecomm.repository.UserRepository;
import com.ecomm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDtos.UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(u -> UserDtos.UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .build()).toList();
    }
}
