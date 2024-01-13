package com.jpa.finalapp.repository.member;

import com.jpa.finalapp.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalLong;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
//    회원가입은 공통 레포지토리에서 제공하는 save()로 처리한다.

//    로그인
    @Query("SELECT m.id FROM Member m WHERE m.loginId = :loginId AND m.password = :password")
    Optional<Long> findIdByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password")String password);
}
