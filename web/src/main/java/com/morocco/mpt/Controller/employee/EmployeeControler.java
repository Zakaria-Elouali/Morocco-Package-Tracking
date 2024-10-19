package com.morocco.mpt.Controller.employee;


import com.morocco.mpt.domain.employee.Employee;
import com.morocco.mpt.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class EmployeeControler {
    private EmployeeService employeeservice;
    @GetMapping("Employee")
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> allEmployee = employeeservice.getAll();
        return new ResponseEntity<>(allEmployee, HttpStatus.OK);

    }
}
