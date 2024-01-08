package com.jpa.finalapp.domain.entity.product;


import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "JPA_BOOK")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("BOOK")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
public class Book extends Product{
    private String author;
    private String publisher;


    @Builder
    public Book(Long id, String name, Integer price, Integer quantity, String author, String publisher) {
        super(id, name, price, quantity);
        this.author = author;
        this.publisher = publisher;
    }
}











