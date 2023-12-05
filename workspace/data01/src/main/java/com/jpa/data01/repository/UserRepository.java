package com.jpa.data01.repository;

import com.jpa.data01.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;
    public User save(User user){
        em.persist(user);
        return user;
    }
    public Optional<User> findById(Long bookId){
        return Optional.ofNullable(em.find(User.class,bookId));
    }
    public List<User> findAll(){
        String query = "SELECT u FROM User u";
        return em.createQuery(query,User.class).getResultList();
    }
    public void delete(User user){
        em.remove(user);
    }
}
