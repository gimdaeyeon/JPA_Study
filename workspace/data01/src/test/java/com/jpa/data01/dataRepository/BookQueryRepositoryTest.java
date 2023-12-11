package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.dto.BookDto;
import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.type.BookCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class BookQueryRepositoryTest {
    @Autowired BookQueryRepository bookQueryRepository;

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

        bookQueryRepository.saveAll(List.of(book1, book2, book3));
    }
    @Test
    void findNameList() {
        List<String> nameList = bookQueryRepository.findNameList();
        System.out.println("nameList = " + nameList);
    }
    @Test
    void finBooksChNull(){
        List<Book> booksChNull = bookQueryRepository.findBooksChNull();
        System.out.println("booksChNull = " + booksChNull);
    }
    @Test
    void findBookQuery(){
        List<Book> bookList = bookQueryRepository.findBookQuery(BookCategory.IT, LocalDateTime.of(2000, 1, 1, 12, 30));
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findByIn(){
        List<Book> bookList = bookQueryRepository.findByIn(List.of(BookCategory.IT, BookCategory.HISTORY));
        System.out.println("bookList = " + bookList);
    }
    @Test
    void findTotalPriceOfNovel(){
        int totalPriceOfNovel = bookQueryRepository.findTotalPriceOfNovel();
        System.out.println("totalPriceOfNovel = " + totalPriceOfNovel);
    }
    @Test
    void countIdOfGroup(){
        List<Integer> countList = bookQueryRepository.countIdOfGroup();
        System.out.println("countList = " + countList);
    }
    @Test
    void findDtoById(){
        BookDto dtoById = bookQueryRepository.findDtoById(1L);
        System.out.println("dtoById = " + dtoById);
    }
    @Test
    void findDtoByPrice(){
        List<BookDto> dtoByPrice = bookQueryRepository.findDtoByPrice(10000);
        System.out.println("dtoByPrice = " + dtoByPrice);
    }
    @Test
    void findAvgPriceOfCategory(){
        Map<String, Object> avgPriceOfCategory = bookQueryRepository.findAvgPriceOfCategory(BookCategory.IT);
        System.out.println("avgPriceOfCategory = " + avgPriceOfCategory);
    }
    @Test
    void findAvgPricePerCategory(){
        List<Map<String, Object>> mapList = bookQueryRepository.findAvgPricePerCategory();
        mapList.forEach(System.out::println);
    }
    @Test
    void findAllNameWithPrice(){
        List<String> list = bookQueryRepository.findAllNameWithPrice();
        System.out.println("list = " + list);
    }
    @Test
    void findBookByReleaseDateYYYY(){
        List<Book> bookList = bookQueryRepository.findBookByReleaseDateYYYY("2010");
        System.out.println("bookList = " + bookList);
    }
    @Test
    void nativeFind(){
        List<Book> bookList = bookQueryRepository.nativeFind();
        System.out.println("bookList = " + bookList);
    }



}


























