package com.jpa.securityex.repository;

import com.jpa.securityex.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

//    인증 공급자가 사용하는 UserDetailsService에서 사용할 메서드
    Optional<Member> findByLoginId(String loginId);

}
