package ru.mamreyan.onlineuniversity.employee;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository     employeeRepository;
    private final EmployeeModelAssembler assembler;

    EmployeeController(
            EmployeeRepository employeeRepository,
            EmployeeModelAssembler assembler
    ) {
        this.employeeRepository = employeeRepository;
        this.assembler          = assembler;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public EmployeeModelAssembler getAssembler() {
        return assembler;
    }

    @GetMapping ("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = StreamSupport.stream(
                employeeRepository.findAll().spliterator(),
                false
        ).map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
        );
    }

    @GetMapping ("/employees/{id}")
    EntityModel<Employee> one(
            @PathVariable
                    Long id
    ) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @PostMapping ("/employees")
    ResponseEntity<?> newEmployee(
            @RequestBody
                    Employee newEmployee
    ) {
        if (newEmployee.isNotValid()) {
            throw new EmployeeNotValidException(newEmployee);
        }

        EntityModel<Employee> entityModel = assembler.toModel(employeeRepository.save(newEmployee));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping ("/employees/{id}")
    ResponseEntity<?> replaceEmployee(
            @PathVariable
                    Long id,
            @RequestBody
                    Employee newEmployee
    ) {
        if (newEmployee.isNotValid()) {
            throw new EmployeeNotValidException(newEmployee);
        }

        Employee updatedEmployee = employeeRepository.findById(id).map(employee -> {
            employee.setLastName(newEmployee.getLastName());
            employee.setFirstName(newEmployee.getFirstName());
            employee.setMiddleName(newEmployee.getMiddleName());
            employee.setBirthDate(newEmployee.getBirthDate());
            employee.setEmploymentDate(newEmployee.getEmploymentDate());
            employee.setSalary(newEmployee.getSalary());
            employee.setWorkPhone(newEmployee.getWorkPhone());
            employee.setMobilePhone(newEmployee.getMobilePhone());
            employee.setHead(newEmployee.getHead());
            return employeeRepository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepository.save(newEmployee);
        });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping ("/employees/{id}")
    ResponseEntity<?> deleteEmployee(
            @PathVariable
                    Long id
    ) {
        return employeeRepository.findById(id).map(employee -> {
            employeeRepository.delete(employee);
            return ResponseEntity.ok().body("Employee " + id + " was deleted");
        }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}