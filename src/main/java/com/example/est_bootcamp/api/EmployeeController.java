package com.example.est_bootcamp.api;

import com.example.est_bootcamp.core.BaseController;
import com.example.est_bootcamp.core.BaseService;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class EmployeeController extends BaseController<Employee, Long> {
    private final EmployeeService service;

    @Override
    protected BaseService<Employee, Long> getService() {
        return service;
    }
}
