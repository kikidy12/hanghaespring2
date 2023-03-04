package com.example.hanghaespring2.post.repository;

import com.example.hanghaespring2.common.entity.*;
import com.example.hanghaespring2.post.dto.OrderDto;
import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.post.dto.TestDto;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.user.dto.UserDto;
import com.querydsl.core.Query;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaespring2.common.entity.QOrder.*;
import static com.example.hanghaespring2.common.entity.QPost.*;
import static com.example.hanghaespring2.common.entity.QReply.*;
import static com.example.hanghaespring2.common.entity.QUser.*;
import static com.querydsl.core.group.GroupBy.*;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl {

    private final JPAQueryFactory factory;

    public Tuple getPost(Long id) {
        return factory
                .select(post, post.title)
                .from(post)
                .where(post.id.eq(id))
                .leftJoin(post.user)
                .fetchJoin()
                .leftJoin(post.category)
                .fetchJoin()
                .fetchFirst();
    }

    public List<Tuple> getUser() {
        return factory
                .select(user, order)
                .from(user)
                .join(order).on(order.userId.eq(user.id))
                .fetch();
    }

    public List<TestDto> getUser2() {

        return factory
                .from(user)
                .leftJoin(order).on(order.userId.eq(user.id))
                .transform(groupBy(user.id).list(Projections.constructor(TestDto.class,
                        user.id,
                        user.username,
                        list(Projections.constructor(OrderDto.class,
                                order.id,
                                order.userId
                        ))
                )));
    }

    public List<PostDto.PostRes> getPost3() {

        return factory
                .from(post)
                .leftJoin(user).on(user.eq(post.user))
                .leftJoin(reply).on(reply.post.id.eq(post.id))
                .transform(
                        groupBy(post.id).list(
                                Projections.constructor(
                                        PostDto.PostRes.class,
                                        post,
                                        Projections.constructor(
                                                UserDto.UserRes.class,
                                                user
                                        ),
                                        list(
                                                Projections.constructor(
                                                        ReplyDto.ReplyRes.class,
                                                        reply.id,
                                                        reply.message
                                                )
                                        )
                                )
                        )
                );
    }


    public List<PostDto.PostRes> getPost4(Pageable pageable) {

        return factory
                .from(post)
                .leftJoin(user).on(user.eq(post.user))
                .leftJoin(reply).on(reply.post.id.eq(post.id))
                .transform(
                        groupBy(post.id).list(
                                Projections.constructor(
                                        PostDto.PostRes.class,
                                        post,
                                        Projections.constructor(
                                                UserDto.UserRes.class,
                                                user
                                        ),
                                        list(
                                                Projections.constructor(
                                                        ReplyDto.ReplyRes.class,
                                                        reply.id,
                                                        reply.message
                                                )
                                        )
                                )
                        )
                );
    }

}


