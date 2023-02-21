package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "reply", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReplyLikeUser> likeUsers;


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt desc")
    @BatchSize(size = 100)
    private Set<Reply> children;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Reply parent;

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


    public void setPostNull() {
        post = null;
    }

    @Builder
    public Reply(String message, User user, Post post, Reply parent) {
        this.message = message;
        this.user = user;
        this.post = post;
        this.parent = parent;
    }

    public void addLikeUser(ReplyLikeUser likeUser) {
        this.likeUsers.add(likeUser);
        if (!likeUser.getReply().equals(this)) {
            likeUser.setReply(this);
        }
    }


    public void removeLikeUser(User user) {
        this.likeUsers.stream().filter(v -> v.getUser().getId().equals(user.getId())).findFirst().ifPresent(v -> this.likeUsers.remove(v));
    }

    public ReplyDto.ReplyRes res() {
        ReplyDto.ReplyRes.ReplyResBuilder builder = ReplyDto.ReplyRes.builder();

        builder
                .id(this.id)
                .likeCount(this.likeUsers.size())
                .message(this.message);

        if (!this.children.isEmpty()) {
            builder.children(this.children.stream().map(Reply::res).collect(Collectors.toList()));
        }
        else {
            builder.children(new ArrayList<>());
        }
        return builder.build();
    }
    public void update(String message) {
        this.message = message;
    }
}
