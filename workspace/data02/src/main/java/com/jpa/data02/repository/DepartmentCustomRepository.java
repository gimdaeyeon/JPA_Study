package com.jpa.data02.repository;

import com.jpa.data02.domain.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentCustomRepository {
    List<Department> findCustomByName(String name);

}
