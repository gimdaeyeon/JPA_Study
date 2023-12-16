package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBulkRepository extends JpaRepository<Book,Long> {
    @Modifying // 이 어노테이션을사용해야 excuteUpdate()로 실행된다.
    @Query("update Book b set b.name = '수정완료' where b.id<10 ")
    void updateBook1();

    @Modifying(clearAutomatically = true) // 자동 클리어
    @Query("update Book b set b.name = '수정완료'," +
           "b.modifiedDate = current_timestamp where b.id<:id ")
    void updateBook2(@Param("id")Long id);




}























