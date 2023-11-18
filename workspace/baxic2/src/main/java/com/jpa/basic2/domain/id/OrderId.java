package com.jpa.basic2.domain.id;

import lombok.Data;

import java.io.Serializable;

//복합키를 위한 식별자 클래스
//1. 반드시 기본생성자가 있어야한다.
//2. 반드시 hashCode(), equals()를 재정의 해야한다.
//3. Serializable를 구현해야한다.
@Data
public class OrderId implements Serializable {
    private Long member; //Order 클래스의 member필드와 연결됨
    private Long product; //Order 클래스의 product필드와 연결됨
}
