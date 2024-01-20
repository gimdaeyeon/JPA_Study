package com.jpa.finalapp.service.member;

import com.jpa.finalapp.domain.dto.member.MemberJoinDto;
import com.jpa.finalapp.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

//    매개변수에 엔티티를 선언하면 컨트롤러에서 엔티티를 넘겨줘야한다는 의미이다.
//    이 내용은 선택사항이지만 일반적으로 엔티티는 서비스 단계까지만 다루는 것을 권장한다.
//    컨트롤러 3-layer 중 화면 계층에 속한다. 화면 계층에 DB와 연결된 엔티티를 노출시키지 않는 것이
//    보안, 성능, 유연성 측면에서 좋다.
    @Override
    public void join(MemberJoinDto memberJoinDto) {
        memberRepository.save(memberJoinDto.toEntity());
    }

//    읽기 전용으로 설정 시 select만 가능하다.(수정, 삭제, 삽입이 불가능함!)
//    변경감지(dirty checking(이 일어날 필요가 없으므로 스냅샷에 엔티티 초기상태를 저장하지 않는다.(공간 절약)
//    읽기 전용이므로 메소드 종료시 flush가 발생되지 않아 불필요한 동작 생략
    @Override
    @Transactional(readOnly = true)
    public Long login(String loginId, String password) {
        return memberRepository.findIdByLoginIdAndPassword(loginId,password)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 회원정보 입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public String findLoginId(Long memberId) {
        return memberRepository.findLoginId(memberId)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 회원정보 입니다."));
    }

}















