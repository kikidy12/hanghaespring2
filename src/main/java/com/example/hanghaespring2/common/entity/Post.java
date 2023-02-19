package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.post.dto.PostDto;
import jdk.jfr.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("createdAt desc")
    private List<Reply> replies;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLikeUser> likeUsers;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PostCategory category;

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getPostList().remove(this);
        }

        this.user = user;

        if(!user.getPostList().contains(this)) {
            user.addPost(this);
        }
    }


    public void addReply(Reply reply) {
        this.replies.add(reply);
        if (!reply.getPost().equals(this)) {
            reply.setPost(this);
        }
    }

    public void addLikeUser(PostLikeUser likeUser) {
        this.likeUsers.add(likeUser);
        if (!likeUser.getPost().equals(this)) {
            likeUser.setPost(this);
        }
    }

    public void removeLikeUser(User user) {
        this.likeUsers.stream().filter(v -> v.getUser().getId().equals(user.getId())).findFirst().ifPresent(v -> this.likeUsers.remove(v));
    }


    @Builder
    public Post(PostDto.PostAdd dto, User user, PostCategory category) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.user = user;
        this.category = category;
    }

    public PostDto.PostRes res () {

        return PostDto.PostRes
                .builder()
                .id(this.id)
                .content(this.content)
                .title(this.title)
                .username(this.user.getUsername())
                .replies(this.getReplies())
                .category(this.category)
                .likeCount(this.likeUsers.size())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public PostDto.PostResNoReply resNoReply () {

        return PostDto.PostResNoReply
                .builder()
                .id(this.id)
                .content(this.content)
                .category(this.category)
                .title(this.title)
                .username(this.user.getUsername())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public void update(PostDto.PostUpdate dto) {
        this.title = ObjectUtils.defaultIfNull(dto.getTitle(), this.title);

        this.content = ObjectUtils.defaultIfNull(dto.getContent(), this.content);
    }
}
