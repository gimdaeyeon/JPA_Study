package com.jpa.finalapp.domain.dto.member;

import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.type.member.MemberGender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data @NoArgsConstructor
public class MemberJoinDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDate birth;
    private String phoneNumber;
    private String gender;
    private String address;
    private String addressDetail;
    private String zipcode;

}






