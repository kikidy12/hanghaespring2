package com.example.hanghaespring2.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private Long userId;

    public OrderDto(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }
}
