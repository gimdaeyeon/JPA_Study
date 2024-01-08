package com.jpa.finalapp.domain.embedded.member;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter @ToString @EqualsAndHashCode
@NoArgsConstructor
public class MemberAddress {
    private String address;
    private String addressDetail;
    private String zipcode;

    public MemberAddress(String address, String addressDetail, String zipcode) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;
    }
}












