package com.jpa.data01.customRepository;

import com.jpa.data01.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BookRepo")
public interface BookRepository extends JpaRepository<Book,Long>, BookRepositoryCustom{
//    사용자 정의 레파지토리를 공통 인터페이스를 상속받는 일반 레파지토리에 같이 상속처리한다.


}
