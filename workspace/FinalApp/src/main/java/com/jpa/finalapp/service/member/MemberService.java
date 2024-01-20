package com.jpa.finalapp.service.member;

import com.jpa.finalapp.domain.dto.member.MemberJoinDto;
import com.jpa.finalapp.domain.entity.member.Member;
import org.springframework.stereotype.Service;

public interface MemberService {
    void join(MemberJoinDto memberJoinDto);
    Long login(String loginId, String password);

    String findLoginId(Long memberId);

}
