package com.jpa.finalapp.domain.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "JPA_COVER")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("COVER")
@PrimaryKeyJoinColumn(name = "COVER_ID")
public class Cover extends Product{
    private String production;
    @Column(name = "COVER_SIZE")
    private Integer size;

    @Builder
    public Cover(Long id, String name, Integer price, Integer quantity, String production, Integer size) {
        super(id, name, price, quantity);
        this.production = production;
        this.size = size;
    }
}










