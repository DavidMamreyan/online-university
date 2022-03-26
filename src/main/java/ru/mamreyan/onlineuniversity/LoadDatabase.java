package ru.mamreyan.onlineuniversity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mamreyan.onlineuniversity.employee.Employee;
import ru.mamreyan.onlineuniversity.employee.EmployeeRepository;

import java.util.Collection;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            EmployeeRepository employeeRepository
    ) {
        return args -> {
            Iterable<Employee> employees = employeeRepository.findAll();

            if (employees instanceof Collection && ((Collection<Employee>) employees).size() <= 1) {

                employees = employeeRepository.findAll();
            }

            employees.forEach(employee -> log.info("Employees:\n" + employee));
        };
    }
}