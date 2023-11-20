package com.jpa.basic3.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
/*
JPA_BOOK
구분컬럼에 들아갈 값은 "B"
PK컬럼 이름은 "BOOK_ID"
PRODUCT와 상속관계 맺기
추가 필드 author, publisher
 */
@Entity
@Table(name = "JPA_BOOK")
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
@Getter @Setter
public class Book extends Product{
    private String author;  //  작가
    private String publisher;   // 출판사

}
