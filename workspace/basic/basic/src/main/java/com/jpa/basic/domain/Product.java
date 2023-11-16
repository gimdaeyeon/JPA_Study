package com.jpa.basic.domain;

import com.jpa.basic.domain.type.ProductStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "JPA_PRODUCT")
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String brand;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus productStatus;
    // LocalDate : 시간은 사용하지 않을 때
    // LocalDateTime : 시간도 사용할 때 
    private LocalDate releaseDate;
}
