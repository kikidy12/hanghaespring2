package com.example.hanghaespring2.user.dto;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

public class UserDto {
    @Getter
    @Setter
    @Builder
    public static class UserAdd {
        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private final String username;

        @Pattern(regexp = "^[0-9A-z]{8,}$")
        private String password;

        @Schema(defaultValue = "USER")
        @NotEmpty
        private final UserRoleEnum role;
    }

    @Getter
    @Builder
    public static class UserLogin {
        @NotEmpty
        private final String username;

        @NotEmpty
        private String password;

        @NotEmpty
        private final UserRoleEnum role;
    }

    @Getter
    public static class UserRes {
        private final Long id;
        private final String username;
        private final UserRoleEnum role;

        @Builder
        public UserRes(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
        }
    }
}
