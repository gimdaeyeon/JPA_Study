package org.jpa.data02.doamin.entity;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EntityTest {

    @Test
    void entityTest(){
        Employee emp = Employee.builder()
                .salary(100)
                .hireDate(LocalDate.of(2000, 10, 10))
                .build();

        System.out.println("emp = " + emp);
    }










}