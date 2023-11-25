package com.jpa.basic3.domain2.embedded;

import lombok.*;

import javax.persistence.Embeddable;




@Embeddable // 이메베디드 타입은 기본생성자가 필수이다.
@Getter
//이렇게 만든 임베디드 타입은 연관된 값들을 그룹화시키는 것에 목적이 있으며,
//조회시 각 필드르 따로 사용하야하는 것 보다는
//모두 사용하는 목적으로 설계하는 것이 좋다.
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address {
    private String address;
    private String addressDetail;
    private String zipcode;

    //    임베디드 타입 클래스에 값을 위한 메소드를 자유롭게 만들어도 된다.
    public String createFullAddress(){  // 메소드 이름에 get을 넣지 않는다.
        return address+" "+addressDetail+" ["+zipcode+"]";
    }
}
