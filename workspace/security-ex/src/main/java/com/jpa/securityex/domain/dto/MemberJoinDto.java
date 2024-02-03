package com.jpa.securityex.domain.dto;

import com.jpa.securityex.domain.entity.Member;
import com.jpa.securityex.domain.type.Role;
import lombok.Data;

@Data
public class MemberJoinDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private Role role;

    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
