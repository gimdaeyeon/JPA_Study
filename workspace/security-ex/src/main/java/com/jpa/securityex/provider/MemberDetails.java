package com.jpa.securityex.provider;


import com.jpa.securityex.domain.entity.Member;
import com.jpa.securityex.domain.type.Role;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter @ToString
public class MemberDetails implements UserDetails {
//    회원 정보에서 필요한 필드를 선언
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private Role role;

//    스프링 시큐리티에서 권환을 확인한기 위해 필요한 컬렉션
    private Collection<? extends GrantedAuthority> authorities;

//    엔티티를 넘기든, dto를 넘기든, 빌더패턴을 사용하든 원하는 방식을 사용하면된다.
    public MemberDetails(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.role = member.getRole();
//        스프링 시큐리티 내부에서 role을 사용한느 경우 ROLE_ 가 앞에 붙어야 한다.
        this.authorities = AuthorityUtils.createAuthorityList(member.getRole().getSecurityRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        계정의 권한 목록을 반환하는 메서드
        return this.authorities;
    }

    @Override
    public String getPassword() {
//        비밀번호 반환 메서드
        return this.password;
    }

    @Override
    public String getUsername() {
//        아이디 반환 메서드
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
//        계정의 만료 여부를 반환하는 메서드
//        이름에 non이 붙었으므로 true를 반환하면 만료되지 않았다는 의미
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        계정의 잠금 여부를 반환하는 메서드
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        비밀번호의 만료 여부를 반환하는 메서드
//        true : 만료 안됨
        return true;
    }

    @Override
    public boolean isEnabled() {
//        계정의 활성화 여부를 반환하는 메서드
//        true : 활성화
        return true;
    }
}
