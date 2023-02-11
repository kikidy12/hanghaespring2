package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.auth.dto.AuthDto;
import com.example.hanghaespring2.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    List<Post> postList = new ArrayList<>();

    public void addPost(Post post) {
        this.postList.add(post);
        if (post.getUser() != this) {
            post.setUser(this);
        }
    }

    @Builder
    public User(AuthDto.UserAdd dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }

    public UserDto.UserRes res() {
        return UserDto.UserRes.builder().user(this).build();
    }
}
