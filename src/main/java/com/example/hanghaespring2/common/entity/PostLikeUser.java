package com.example.hanghaespring2.common.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Entity
public class PostLikeUser extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void setPost(Post post) {
        if(this.post != null) {
            this.post.getLikeUsers().remove(this);
        }
        this.post = post;
        if(!post.getLikeUsers().contains(this)) {
            post.addLikeUser(this);
        }
    }

    @Builder
    public PostLikeUser(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }
}
