package com.jpa.finalapp.domain.entity.product;

import com.jpa.finalapp.domain.base.Period;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "JPA_PRODUCT")
@SequenceGenerator(name = "SEQ_PRODUCT_GENERATOR", sequenceName = "SEQ_PRODUCT")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Product extends Period {
    @Id @GeneratedValue(generator = "SEQ_PRODUCT_GENERATOR")
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;

    public Product(Long id, String name, Integer price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}











