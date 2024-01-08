package com.jpa.finalapp.domain.entity.member;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.type.member.MemberGender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Entity
@Table(name = "JPA_MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER_GENERATOR", sequenceName = "SEQ_MEMBER")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Member extends Period {
    @Id @GeneratedValue(generator = "SEQ_MEMBER_GENERATOR")
    @Column(name = "MEMBER_ID")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDate birth;
    private String phoneNumber;
    @Enumerated(EnumType.STRING) @ColumnDefault("'NONE'")
    private MemberGender gender;
    @Embedded
    private MemberAddress memberAddress;

    @Builder
    public Member(Long id, String loginId, String password, String name, LocalDate birth, String phoneNumber, MemberGender gender, MemberAddress memberAddress) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.memberAddress = memberAddress;
    }
}






















