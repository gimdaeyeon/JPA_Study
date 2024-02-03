package com.jpa.securityex.domain.entity;

import com.jpa.securityex.domain.type.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "SEC_MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER_GENERATOR", sequenceName = "SEQ_SEC_MEMBER")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(generator = "SEQ_MEMBER_GENERATOR")
    @Column(name = "MEMBER_ID")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String loginId, String password, String name, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
