package com.ffs.repository;

import com.ffs.dao.Employee;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends Repository<Employee, Long> {

    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();
}
