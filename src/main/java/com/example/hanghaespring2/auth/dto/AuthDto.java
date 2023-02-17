package com.example.hanghaespring2.auth.dto;

import com.example.hanghaespring2.common.entity.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

public class AuthDto {
    @Getter
    @Setter
    @Builder
    public static class UserAdd {
        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private final String username;

        @Pattern(regexp = "^[0-9A-z]{8,}$")
        private String password;

        @Schema(defaultValue = "USER")
        private final UserRoleEnum role;

        private final String adminToken;
    }

    @Getter
    @Builder
    public static class Login {

        @Schema(defaultValue = "test1234")
        private final String username;
        @Schema(defaultValue = "test1234")
        private String password;

        private final UserRoleEnum role;
    }
}
