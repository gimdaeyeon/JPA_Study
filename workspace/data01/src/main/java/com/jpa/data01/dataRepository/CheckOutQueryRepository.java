package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckOutQueryRepository extends JpaRepository<CheckOut,Long> {

//    회원별 도서 대여목록
    @Query("select new Map(u.id as userId, u.name as userName, b.name as bookName) from User u join  CheckOut c on u.id = c.user.id join Book b on c.book.id = b.id")
    List<Map<String,Object>> findUsersCheckOutBook();

}
