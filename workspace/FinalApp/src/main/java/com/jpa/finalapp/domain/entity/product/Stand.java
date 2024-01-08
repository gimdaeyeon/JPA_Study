package com.jpa.finalapp.domain.entity.product;

import com.jpa.finalapp.domain.type.product.StandMaterials;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "JPA_STAND")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("STAND")
@PrimaryKeyJoinColumn(name = "STAND_ID")
public class Stand extends Product{
    private String production;
    @Enumerated(EnumType.STRING)
    private StandMaterials standMaterials;

    @Builder
    public Stand(Long id, String name, Integer price, Integer quantity, String production, StandMaterials standMaterials) {
        super(id, name, price, quantity);
        this.production = production;
        this.standMaterials = standMaterials;
    }
}
