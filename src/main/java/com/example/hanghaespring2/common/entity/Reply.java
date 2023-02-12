package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getReplies().remove(this);
        }
        this.user = user;
        if(!user.getReplies().contains(this)) {
            user.addReply(this);
        }
    }

    public void setPost(Post post) {
        if(this.post != null) {
            this.post.getReplies().remove(this);
        }
        this.post = post;
        if(!post.getReplies().contains(this)) {
            post.addReply(this);
        }
    }

    @Builder
    public Reply(String message, User user, Post post) {
        this.message = message;
        this.setUser(user);
        this.setPost(post);
    }

    public ReplyDto.ReplyRes res() {
        return ReplyDto.ReplyRes.builder().id(this.id).message(this.message).build();
    }

    public void update(String message) {
        this.message = message;
    }
}
