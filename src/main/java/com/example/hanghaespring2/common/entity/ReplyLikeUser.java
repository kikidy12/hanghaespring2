package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.reply.dto.ReplyDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "reply_id"}))
@Entity
public class ReplyLikeUser extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    public void setReply(Reply reply) {
        if(this.reply != null) {
            this.reply.getLikeUsers().remove(this);
        }
        this.reply = reply;
        if(!reply.getLikeUsers().contains(this)) {
            reply.addLikeUser(this);
        }
    }

    @Builder
    public ReplyLikeUser(Long id, User user, Reply reply) {
        this.id = id;
        this.user = user;
        this.reply = reply;
    }
}
