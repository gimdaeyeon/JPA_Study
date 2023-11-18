package com.jpa.basic2.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "JPA_PRODUCT")
@Getter @Setter
@ToString(exclude = {"members"})
@SequenceGenerator(
        name = "SEQ_PRODUCT_GENERATOR",
        sequenceName = "SEQ_JPA_PRODUCT"
)
public class Product {
    @Id
    @GeneratedValue(generator = "SEQ_PRODUCT_GENERATOR")
    private Long id;
    private String name;
    private int price;
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();
}
