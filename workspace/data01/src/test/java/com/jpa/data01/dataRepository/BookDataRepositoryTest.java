package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.type.BookCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional @Commit
class BookDataRepositoryTest {
    @Autowired
    BookDataRepository bookDataRepository;
    @PersistenceContext
    EntityManager em;
    @BeforeEach
    void setUp(){
        Book book1 = new Book();
        book1.setCategory(BookCategory.IT);
        book1.setName("JPA");
        book1.setPrice(10_000);
        book1.setReleaseDate(LocalDate.of(2023, 11, 11));

        Book book2 = new Book();
        book2.setCategory(BookCategory.NOVEL);
        book2.setName("해리포터");
        book2.setPrice(20_000);
        book2.setReleaseDate(LocalDate.of(2000, 1, 12));

        Book book3 = new Book();
        book3.setCategory(BookCategory.HISTORY);
        book3.setName("세계로");
        book3.setPrice(15_000);
        book3.setReleaseDate(LocalDate.of(2010, 7, 23));

        bookDataRepository.saveAll(List.of(book1,book2,book3));
        em.flush();
        em.clear();
    }

    @Test
    void save(){
        Book book = new Book();
        book.setName("test");
        book.setPrice(10_000);
        book.setCategory(BookCategory.IT);
        book.setReleaseDate(LocalDate.of(2000,10,10));

        bookDataRepository.save(book);
    }
    @Test
    void find(){
        bookDataRepository.findById(2L).ifPresent(System.out::println);
        bookDataRepository.findAll().forEach(System.out::println);
    }
    @Test
    void delete(){
        bookDataRepository.deleteById(1L);
        bookDataRepository.deleteAll();
    }
    @Test
    void findBy(){
        List<Book> bookList = bookDataRepository.findBy();
    }
    @Test
    void findByName(){
        List<Book> bookList = bookDataRepository.findByName("해리포터");
    }
    @Test
    void findTestByPrice(){
        List<Book> testByPrice = bookDataRepository.findTestByPrice(10_000);
    }
    @Test
    void findOptionalTest(){
        Optional<Book> foundBook = bookDataRepository.findOptionalByName("");
        System.out.println(foundBook.isPresent());

        Book bookByName = bookDataRepository.findBookByName("");
        System.out.println("bookByName = " + bookByName);
    }
    @Test
    void findByPriceGreaterThan(){
        List<Book> bookList = bookDataRepository.findByPriceGreaterThan(10000);
        List<Book> bookList2 = bookDataRepository.findByPriceLessThan(10000);
    }
    @Test
    void findByReleaseDateGreaterThanEqual(){
        List<Book> bookList = bookDataRepository.findByReleaseDateGreaterThanEqual(LocalDate.of(2000,1,1));
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findByReleaseDateAfter(){
        List<Book> byReleaseDateAfter = bookDataRepository.findByReleaseDateAfter(LocalDate.of(2000, 1, 1));
        System.out.println("byReleaseDateAfter = " + byReleaseDateAfter);
    }
    @Test
    void findByNameLike(){
        List<Book> bookList = bookDataRepository.findByNameLike("J");
        List<Book> bookList2 = bookDataRepository.findByNameNotLike("J");
        System.out.println("bookList = " + bookList);
        System.out.println("bookList2 = " + bookList2);
    }
    @Test
    void containing(){
        List<Book> bookList = bookDataRepository.findByNameContaining("J");
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findByNameStartingWith(){
        List<Book> bookList = bookDataRepository.findByNameStartingWith("J");
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findByPriceIsNull(){
        List<Book> byPriceIsNull = bookDataRepository.findByPriceIsNull();
        System.out.println("byPriceIsNull = " + byPriceIsNull);
    }
    @Test
    void findByPriceBetween(){
        List<Book> byPriceBetween = bookDataRepository.findByPriceBetween(10000, 12000);
        System.out.println("byPriceBetween = " + byPriceBetween);
    }
    @Test
    void in(){
        List<Book> bookList = bookDataRepository.findByNameIn(List.of("해리포터", "JPA", "테스트"));
        System.out.println("bookList = " + bookList);
    }
    @Test
    void notIn(){
        List<Book> bookList = bookDataRepository.findByNameNotIn(List.of("해리포터", "JPA", "테스트"));
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findByNameAndPrice(){
        List<Book> bookList = bookDataRepository.findByNameAndPrice("JPA", 10000);
        System.out.println("bookList = " + bookList);
    }

}












