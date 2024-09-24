package com.morocco.mpt.persistence.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import com.morocco.mpt.domain.employee.Employee;

public interface EmployeeSpringRepository extends JpaRepository<Employee, Long>{ }
