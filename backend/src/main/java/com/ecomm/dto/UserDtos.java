package com.ecomm.dto;

import com.ecomm.entity.Role;
import lombok.*;

public class UserDtos {
    @Getter @Builder @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        private Role role;
    }
}
