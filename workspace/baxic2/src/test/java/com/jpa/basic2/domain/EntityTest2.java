package com.jpa.basic2.domain;

import com.jpa.basic2.domain.id.OrderId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Commit
@Slf4j
class EntityTest2 {
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void save() {
        Product product = new Product();
        product.setName("아이폰");
        product.setPrice(1_000_000);

        entityManager.persist(product);

        Member member = new Member();
        member.setName("김철수");
        member.setAddress("강남구");

        entityManager.persist(member);

        Order order = new Order();
        order.setMember(member);
        order.setProduct(product);
        order.setAmount(10);

        entityManager.persist(order);
    }

    @Test
    void find(){
        OrderId orderId = new OrderId();
        orderId.setMember(1L);
        orderId.setProduct(1L);
//      복합키(조합키)를 사용하는 경우 별도로 만든 식별자 클래스르 이용한다.
        Order order = entityManager.find(Order.class,orderId);
        
        String addr = order.getMember().getAddress();
        System.out.println("addr = " + addr);


    }


//    @Test
//    void save(){
//
//        Product product = new Product();
//        product.setName("아이폰");
//        product.setPrice(1_000_000);
//
//        entityManager.persist(product);
//
//        Member member = new Member();
//        member.setName("김철수");
//        member.setAddress("강남구");
//        member.getProducts().add(product);
//
//        entityManager.persist(member);
//    }
//    @Test
//    void find(){
//        Product product = entityManager.find(Product.class,1L);
//        System.out.println("product = " + product);
//
//        product.getMembers().forEach(System.out::println);
//    }

}