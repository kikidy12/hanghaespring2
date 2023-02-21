package com.example.hanghaespring2.reply.repository;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.Reply;
import com.example.hanghaespring2.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByIdAndUser(Long id, User user);

}
