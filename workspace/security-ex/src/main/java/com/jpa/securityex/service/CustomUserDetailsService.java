package com.jpa.securityex.service;

import com.jpa.securityex.domain.entity.Member;
import com.jpa.securityex.provider.MemberDetails;
import com.jpa.securityex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
//    실제 DB에 저장된 회원정보를 가져와야하므로 memberRepository를 주입받아 DB접근을 수행한다.
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetailsService를 구현할 때 반드시 재정의해야하는 메소드
//        이 메소드는 username(로그인 아이디)를 매개변수로 받아 회원 정보를 불러온다.
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 회원아이디입니다."));
        return new MemberDetails(member);
    }
}
