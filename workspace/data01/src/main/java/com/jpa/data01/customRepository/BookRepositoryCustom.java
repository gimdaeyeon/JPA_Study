package com.jpa.data01.customRepository;

import com.jpa.data01.domain.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {
//    사용자 정의 Repository를 만들기 위해 필요한 인터페이스 : 사용자 정의 인터페이스
    List<Book> findCustomByName(String name);
}
