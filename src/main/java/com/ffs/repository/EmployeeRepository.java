package com.ffs.repository;

import com.ffs.dao.Employee;
import org.springframework.data.repository.Repository;

public interface EmployeeRepository extends Repository<Employee, Long> {
}
