package com.jpa.finalapp.domain.entity.order;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "JPA_ORDER_PRODUCT")
@SequenceGenerator(name = "SEQ_ORDER_PRODUCT_GENERATOR", sequenceName = "SEQ_ORDER_PRODUCT")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends Period {
    @Id @GeneratedValue(generator = "SEQ_ORDER_PRODUCT_GENERATOR")
    @Column(name = "ORDER_PRODUCT_ID")
    private Long id;
    private Integer price;
    private Integer count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public OrderProduct(Long id, Integer price, Integer count, Order order, Product product) {
        this.id = id;
        this.price = price;
        this.count = count;
        this.order = order;
        this.product = product;
    }
}










