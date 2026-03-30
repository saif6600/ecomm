package com.ecomm.service.impl;

import com.ecomm.exception.ApiException;
import com.ecomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User not found")).getId();
    }
}
