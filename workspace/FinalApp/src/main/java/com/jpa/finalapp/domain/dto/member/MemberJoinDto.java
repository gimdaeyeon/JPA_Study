package com.jpa.finalapp.domain.dto.member;

import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.domain.type.member.MemberGender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class MemberJoinDto {
//    유효성 검증 어노테이션은 여러 종류가 있다.

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String birth;
    private String phoneNumber;
    private String gender;
    private String address;
    private String addressDetail;
    private String zipcode;

//    Dto -> Entity
//    화면에서 전달 받은 Dto를 Entity로 변환하는 일이 많은데 그때마다 개발자가 일일이 변환하는 작업은
//    반복적이고 번거롭다.
//    그러므로 반복저긍롯 사용할 수 있고 쉽게 변환할 수 있는 기능을 만든다.
//    toEntity()를 반드시 Dto 내부에 만들어야 하는것은 아니며 여러 방법이 존재한다.
    public Member toEntity() {
        MemberAddress memberAddress = new MemberAddress(address, addressDetail, zipcode);
//        enum의 valuOf()는 문자여을 받아 해당 문자열과 일치하는 enum객체를 받아온다.
//        "MALE" -> MALE 객체를 반환
        MemberGender memberGender = MemberGender.valueOf(gender);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birth, formatter);

        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .birth(birthDate)
                .gender(memberGender)
                .memberAddress(memberAddress)
                .phoneNumber(phoneNumber)
                .build();
    }

//    Entity - Dto
//    Entity 에서 Dto로 변환하는 메소드
//    여러 방식으로 만들 수 있다.
//    우리는 static factory method라는 디자인 패턴을 적용할 것이다.
//    이 디자인 패턴에서 권장하는 네이밍 컨벤션은 from, of, getInstance, create - 등이 있다.
//    from은 매개변수가  1개인 경우, of는 매개변수가 2개 이상인경우 사용을 권장한다.
//    이런 디자인 패턴을 사용하는 이유는 반복 코드를 줄이고 메소드의 의도가 명확해지기 때문이다.
    public static MemberJoinDto from(Member member){
        MemberJoinDto dto = new MemberJoinDto();
        dto.setId(member.getId());
        dto.setLoginId(member.getLoginId());
        dto.setPassword(member.getPassword());
        dto.setName(member.getName());
        dto.setBirth(member.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setGender(member.getGender().name());
        dto.setAddress(member.getMemberAddress().getAddress());
        dto.setAddressDetail(member.getMemberAddress().getAddressDetail());
        dto.setZipcode(member.getMemberAddress().getZipcode());

        return dto;
    }





}






