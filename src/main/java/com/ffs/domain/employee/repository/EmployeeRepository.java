package com.ffs.domain.employee.repository;

import com.ffs.domain.employee.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends Repository<Employee, Long> {

    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    @Query("SELECT e FROM Employee e " +
            "WHERE e.branch.id = :branchId")
    List<Employee> findAllByBranchId(Long branchId);
}
