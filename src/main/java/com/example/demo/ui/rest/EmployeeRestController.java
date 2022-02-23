package com.example.demo.ui.rest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.demo.business.dto.EmployeeDto;
import com.example.demo.business.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeRestController {

    @Autowired
    public EmployeeServices employeeServices;

    // ROOT
    // http://localhost:8080/api/v1/index
    @GetMapping("/index")
    @ResponseBody
    public String  getRoot() {
        return "index Sayfası";
    }

    //LIST
    // http://localhost:8080/api/v1/employees
    @GetMapping("/employees")
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> teacherDto = (List<EmployeeDto>) employeeServices.getAllEmployees();
        return teacherDto;
    }

    //FIND
    // http://localhost:8080/api/v1/employees/1
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        List<EmployeeDto> teacherDto = (List<EmployeeDto>) employeeServices.getAllEmployees();
        return ResponseEntity.ok(teacherDto.get(0));
    }

    //SAVE
    // http://localhost:8080/api/v1/employees
    @PostMapping("/employees")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto teacherDto) {
        employeeServices.createEmployee(teacherDto);
        return teacherDto;
    }

    //UPDATE
    // http://localhost:8080/api/v1/employees/1
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDetails) {
        employeeServices.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(employeeDetails);
    }

    //DELETE
    // http://localhost:8080/api/v1/employees/1
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        employeeServices.deleteEmployee(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}