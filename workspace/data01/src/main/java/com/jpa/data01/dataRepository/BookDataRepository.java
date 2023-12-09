package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDataRepository extends JpaRepository<Book,Long>{
//    Spring Data JPA는 쿼리 메소드를 지원해준다
//    1. 쿼리 메소드는 기본적으로 메소드 이름을 기반으로 쿼리를 생성해준다.
//    2. 메소드 이름에 특정 키워드를 사용해야한다.

//    조회는 find[식별자]By 를 사용한다.
//    findBy()는 기본적인 select from 쿼리를 생성해준다.
//    By앞에 붙는 [식별자]는 이 쿼리를 설명하는 내용이나 식별하기 위한 이름을 작성하면된다. (생략가능)
    List<Book> findBy();

//    조건을 추가하고 싶다면 By뒤에 조건으로 사용할 필드를 작성한다.
//    find[식별자]By[필드]
//    해당 필드로  equals(=) 조건을 추가해준다.
    List<Book> findByName(String name);

//    엔티티에 존재하는 필드라면 모두 조건으로 사용 가능하다.
    List<Book> findTestByPrice(int price);
 /**
    *반환타입*
    쿼리메소드는 다양한 반환타입을 설정할 수 있다.
    entity, List,Optional 등등을 사용할 수 있다.
    옵셔널, 엔티티를 반환타입으로 설정하는 경우 조회결과가 2건이사이면 오류가 발생된다.(주의)
    조회결과가 0건이면 null이 반환된다.
  */
    Optional<Book> findOptionalByName(String name);
    Book findBookByName(String name);



}






















