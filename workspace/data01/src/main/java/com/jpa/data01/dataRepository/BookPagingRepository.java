package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.type.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPagingRepository extends JpaRepository<Book,Long> {

//   페이징 처리를 하기 위해서 반환 타입을 Page로,
//    페이징 조건을 넣기 위해서 매개변수에 Pageable을 사용
    Page<Book> findByCategory(BookCategory bookCategory, Pageable pageable);

    @Query("select b from Book b where b.id >10")
    Page<Book> findBooks(Pageable pageable);





}













