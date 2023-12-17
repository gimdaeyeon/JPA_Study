package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface CheckOutDataRepository extends JpaRepository<CheckOut,Long> {
//    연관관계를 맺은 엔티티로 조회(해당 엔티티의 ID를 이용하여 조회함)
    Optional<CheckOut> findByBook(Book book);
}

























