package com.jpa.finalapp.domain.dto.member;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class MemberLoginDto {
    @NotBlank(message = "아이디를 입력하세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;
}
