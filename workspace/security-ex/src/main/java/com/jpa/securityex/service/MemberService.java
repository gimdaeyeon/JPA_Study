package com.jpa.securityex.service;

import com.jpa.securityex.domain.dto.MemberJoinDto;
import com.jpa.securityex.domain.type.Role;
import com.jpa.securityex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void saveMember(MemberJoinDto memberJoinDto){
        memberJoinDto.setRole(Role.MEMBER);
        memberRepository.save(memberJoinDto.toEntity());
    }
    
    public void login(String loginId, String password){
        memberRepository.findByLoginIdAndPassword(loginId,password)
                .orElseThrow(()->new IllegalStateException("유효하지않은 회원정보"));
    }





}











