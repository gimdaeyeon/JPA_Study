package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.type.BookCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
class BookPagingRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    BookPagingRepository bookPagingRepository;

    @Test
    void jpaPaging() {
        List<Book> resultList = em.createQuery("select b from Book b where b.category =:cate", Book.class)
                .setParameter("cate", BookCategory.IT)
                .setFirstResult(0)  // 데이터 시작 번호 -> (page -1) * amount
                .setMaxResults(10)  // 페이지당 보여줄 데이터 수
                .getResultList();
        System.out.println("resultList = " + resultList);

//        이 외에도 페이징 처리에 필요한 total count 쿼리가 필요하며,
//        화면에 페이징 블록을 뿌리기 위한 여러 연산이 필요하다.
    }

    @Test
    void findByCategory() {
//        sort
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");

        PageRequest pageRequest = PageRequest.of(0, 10, sortById); // page번호를 0부터 사용한다.
        Page<Book> bookPage = bookPagingRepository.findByCategory(BookCategory.IT, pageRequest);

//        Page타입은 페이징 처리에 필요한 모든 정보를 다 가지고 있다.
        List<Book> content = bookPage.getContent();
        System.out.println("조회된 데이터 = " + content);

        long totalElements = bookPage.getTotalElements();
        System.out.println("전체 데이터 수 = " + totalElements);

        int number = bookPage.getNumber();
        System.out.println("현재 페이지 번호 = " + number);

        int totalPages = bookPage.getTotalPages();
        System.out.println("총 페이지 수 = " + totalPages);

        boolean first = bookPage.isFirst();
        System.out.println("현재 첫 번째 페이지 인가? = " + first);
        boolean last = bookPage.isLast();
        System.out.println("현재 마지막 페이지 인가? = " + last);

        boolean hasNext = bookPage.hasNext();
        System.out.println("다음 페이지가 존재하는가? = " + hasNext);
        boolean previous = bookPage.hasPrevious();
        System.out.println("이전 페이지가 존재하는가? = " + previous);
    }

    @Test
    void findByCategory2() {
        PageRequest pageRequest = PageRequest.of(0, 10); // page번호를 0부터 사용한다.
        Page<Book> bookPage = bookPagingRepository.findByCategory(BookCategory.IT, pageRequest);

//        Page타입은 페이징 처리에 필요한 모든 정보를 다 가지고 있다.
        List<Book> content = bookPage.getContent();
        System.out.println("조회된 데이터 = " + content);

        long totalElements = bookPage.getTotalElements();
        System.out.println("전체 데이터 수 = " + totalElements);

        int number = bookPage.getNumber();
        System.out.println("현재 페이지 번호 = " + number);

        int totalPages = bookPage.getTotalPages();
        System.out.println("총 페이지 수 = " + totalPages);

        boolean first = bookPage.isFirst();
        System.out.println("현재 첫 번째 페이지 인가? = " + first);
        boolean last = bookPage.isLast();
        System.out.println("현재 마지막 페이지 인가? = " + last);

        boolean hasNext = bookPage.hasNext();
        System.out.println("다음 페이지가 존재하는가? = " + hasNext);
        boolean previous = bookPage.hasPrevious();
        System.out.println("이전 페이지가 존재하는가? = " + previous);
    }
    @Test
    void findBooks(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Book> books = bookPagingRepository.findBooks(pageRequest);
        System.out.println("books = " + books);

    }


}