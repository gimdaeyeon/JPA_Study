package com.jpa.data01.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter  @ToString @Setter
@AllArgsConstructor @NoArgsConstructor
public class Address {
    private String address;
    private String addressDetail;
    private String zipcode;
}
