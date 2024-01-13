package com.jpa.finalapp.service.member;

import com.jpa.finalapp.domain.dto.member.MemberJoinDto;
import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.domain.type.member.MemberGender;
import com.jpa.finalapp.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .loginId("test")
                .password("password")
                .name("John Doe")
                .birth(LocalDate.of(1990, 1, 1))
                .gender(MemberGender.MALE)
                .memberAddress(new MemberAddress())
                .phoneNumber("1234567890")
                .build();
    }

    @Test
    void join() {
        //given
        doReturn(member).when(memberRepository).save(any());
        //when
        memberService.join(MemberJoinDto.from(member));
        //then
        verify(memberRepository,times(1)).save(any());
    }

    @Test
    void login() {
        //given
        doReturn(Optional.of(1L)).when(memberRepository).findIdByLoginIdAndPassword(any(),any());
        //when
        Long memberId = memberService.login("sdfsd", "dfdf");

        //then
        assertThat(memberId).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인 예외")
    void loginException(){
        // given
        doReturn(Optional.empty()).when(memberRepository).findIdByLoginIdAndPassword(any(),any());
        // when
        // then
        assertThatThrownBy(()-> memberService.login("13","12321"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는");
    }






}






