package com.example.demo.business.services.impl;

import com.example.demo.business.dto.EmployeeDto;
import com.example.demo.business.services.EmployeeServices;
import com.example.demo.data.entity.EmployeeEntity;
import com.example.demo.data.repository.EmployeeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;


    //LIST
    // http://localhost:8080/api/v1/employees
  //  @GetMapping("/employees")
    @Override
    public List<EmployeeDto> getAllEmployees(){
        List<EmployeeDto> listDto = new ArrayList<>();
        Iterable<EmployeeEntity> teacherList = employeeRepository.findAll();
        for (EmployeeEntity entity : teacherList) {
            EmployeeDto teacherDto = EntityToDto(entity);//model
            listDto.add(teacherDto);
        }
        return  listDto;
    }

    //FIND
    // http://localhost:8080/api/v1/employees/1
    // get employee by id rest api
  //  @GetMapping("/employees/{id}")
    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        EmployeeDto teacherDto = EntityToDto(employee);//model
        return ResponseEntity.ok(teacherDto);
    }

    //SAVE
    // http://localhost:8080/api/v1/employees
   // @PostMapping("/employees")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) { //@RequestBody
        EmployeeEntity employeeEntity = DtoToEntity(employeeDto);//ModelMapper
        employeeRepository.save(employeeEntity);
        return employeeDto;
    }

    //DELETE
    // http://localhost:8080/api/v1/employees
  //  @DeleteMapping("/employees/{id}")
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //UPDATE
    // http://localhost:8080/api/v1/employees
//    @PutMapping("/employees/{id}")
    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDetails){
        EmployeeEntity employeeEntity = DtoToEntity(employeeDetails);//ModelMapper

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName( employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setEmailId(employeeEntity.getEmailId());

        EmployeeEntity updatedEmployee = employeeRepository.save(employee);
        EmployeeDto teacherDto = EntityToDto(updatedEmployee);//model
        return ResponseEntity.ok(teacherDto);
    }



    ////////////////////////////////////
    //Model Mapper Entity ==> Dto
    @Override
    public EmployeeDto EntityToDto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);
        return employeeDto;
    }

    //Model Mapper Dto  ==> Entity
    @Override
    public EmployeeEntity DtoToEntity(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
        return employeeEntity;
    }
}