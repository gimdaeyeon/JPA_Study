package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CheckOutDataRepository extends JpaRepository<CheckOut,Long> {

}

























