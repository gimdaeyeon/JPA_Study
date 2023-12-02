package com.jpa.task.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;

//Junit을 사요할 때 스프링 컨테이너의 일부 기느을 사용할 수 있는 확장기능
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)// 특정 컨트롤러와 관련된 빈만 컨테이너에 등록
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;


}