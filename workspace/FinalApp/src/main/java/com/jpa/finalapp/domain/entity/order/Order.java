package com.jpa.finalapp.domain.entity.order;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.domain.type.order.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity @Table(name = "JPA_ORDER")
@SequenceGenerator(name = "SEQ_ORDER_GENERATOR", sequenceName = "SEQ_ORDER") @Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Order  extends Period {
    @Id @GeneratedValue(generator = "SEQ_ORDER_GENERATOR")
    @Column(name = "ORDER_ID")
    private Long id;
    @Enumerated(EnumType.STRING) @ColumnDefault("'ORDER_PENDING'")
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Order(Long id, OrderStatus orderStatus, Member member) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.member = member;
    }
}
