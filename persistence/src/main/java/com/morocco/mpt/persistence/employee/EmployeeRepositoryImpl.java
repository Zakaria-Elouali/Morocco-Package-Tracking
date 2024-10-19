package com.morocco.mpt.persistence.employee;

import com.morocco.mpt.domain.employee.Employee;
import com.morocco.mpt.service.employee.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EmployeeRepositoryImpl implements IEmployeeRepository {

    private final EmployeeSpringRepository employeespringrepository;

    @Override
    public List<Employee> getAll(){
        return employeespringrepository.findAll();
    }
}
