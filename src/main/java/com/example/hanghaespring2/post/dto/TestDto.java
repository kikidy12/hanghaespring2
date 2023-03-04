package com.example.hanghaespring2.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TestDto {

    private Long id;
    private String userName;

    private List<OrderDto> orderDtoList = new ArrayList<>();

    public TestDto(Long id, String userName, List<OrderDto> orderDtoList) {
        this.id = id;
        this.userName = userName;
        this.orderDtoList = orderDtoList;
    }

    public TestDto addOrders(List<OrderDto> orders) {
        List<OrderDto> temps = ObjectUtils.defaultIfNull(orders, new ArrayList<>());
        this.orderDtoList.addAll(temps);
        return this;
    }
}
