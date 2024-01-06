package com.jpa.data02.repository;

import com.jpa.data02.domain.entity.Department;
import com.jpa.data02.domain.entity.QDepartment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jpa.data02.domain.entity.QDepartment.department;

@Repository
public class DepartmentCustomRepositoryImpl implements DepartmentCustomRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public DepartmentCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Department> findCustomByName(String name) {
        return queryFactory.selectFrom(department)
                .where(department.name.eq(name))
                .fetch();
    }
}
