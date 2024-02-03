package com.jpa.securityex.domain.dto;

import lombok.Data;

@Data
public class MemberLoginDto {
    private String loginId;
    private String password;
}
