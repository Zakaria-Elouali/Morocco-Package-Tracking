package com.morocco.mpt.service.employee;

import com.morocco.mpt.domain.employee.Employee;

import java.util.List;

public interface IEmployeeRepository {


    List<Employee> getAll();
}
