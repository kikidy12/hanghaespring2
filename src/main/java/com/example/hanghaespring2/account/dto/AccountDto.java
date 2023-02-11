package com.example.hanghaespring2.account.dto;

import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AccountDto {
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
    }

    @Getter
    @Builder
    public static class Login {

        private final String username;

        private String password;

        private final UserRoleEnum role;
    }
}
