package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.post.dto.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

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

//    @PreRemove
//    private void removeAssociationsWithChilds() {
//        for (Reply e : replies) {
//            e.setPostNull();
//        }
//    }


    @Builder
    public Post(PostDto.PostAdd dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public PostDto.PostRes res () {

        return PostDto.PostRes
                .builder()
                .id(this.id)
                .content(this.content)
                .title(this.title)
                .username(this.user.getUsername())
                .replies(this.getReplies())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public void update(PostDto.PostUpdate dto) {
        this.title = ObjectUtils.defaultIfNull(dto.getTitle(), this.title);

        this.content = ObjectUtils.defaultIfNull(dto.getContent(), this.content);
    }
}
