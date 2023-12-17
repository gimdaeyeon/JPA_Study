package com.jpa.data01.customRepository;

import com.jpa.data01.domain.entity.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom{
//    사용자 정의 Repository
//    구현 클래스는 반드시 인터페이스이름 + Impl이라는 키워드를  클래스이름 뒤에 붙인다.
    private final EntityManager em;

    @Override
    public List<Book> findCustomByName(String name) {
        String query ="select b from Book b where b.name = :name";
        return em.createQuery(query,Book.class)
                .setParameter("name",name)
                .getResultList();
    }


}
