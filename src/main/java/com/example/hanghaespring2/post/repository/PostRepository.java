package com.example.hanghaespring2.post.repository;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
