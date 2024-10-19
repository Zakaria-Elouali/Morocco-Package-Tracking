package com.morocco.mpt.service.employee;

import com.morocco.mpt.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final IEmployeeRepository employeerepository;


    public List<Employee> getAll(){
    List<Employee> allEmployee = employeerepository.getAll();
        return allEmployee;
    }
}
