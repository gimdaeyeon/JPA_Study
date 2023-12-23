package org.jpa.data02.doamin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "JPA_DEPARTMENT")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {
    @Id
    @GeneratedValue
    @Column(name = "DEPARTMENT_ID")
    private Long id;
    private String name;
    private String phone;
    private String officeLocation;

    @Builder
    public Department(Long id, String name, String phone, String officeLocation) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.officeLocation = officeLocation;
    }
}
