package com.ecomm.service;

import com.ecomm.dto.UserDtos;

import java.util.List;

public interface UserService {
    List<UserDtos.UserResponse> getAllUsers();
}
