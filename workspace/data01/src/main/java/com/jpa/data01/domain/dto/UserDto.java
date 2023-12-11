package com.jpa.data01.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//id, name, birth 필드를 가진 UserDto를 만든다.
@Component
@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private LocalDate birth;

    public UserDto(Long id, String name, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }
}
