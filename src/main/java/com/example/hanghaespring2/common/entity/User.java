package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.auth.dto.AuthDto;
import com.example.hanghaespring2.user.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> postList;

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;

    public void addPost(Post post) {
        this.postList.add(post);
        if (!post.getUser().equals(this)) {
            post.setUser(this);
        }
    }

    public void addReply(Reply reply) {
        this.replies.add(reply);
        if (!reply.getUser().equals(this)) {
            reply.setUser(this);
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
