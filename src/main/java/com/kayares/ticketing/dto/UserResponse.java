package com.kayares.ticketing.dto;

import com.kayares.ticketing.domain.Role;
import com.kayares.ticketing.domain.User;

public record UserResponse(
        Long id,
        String username,
        Role role
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
