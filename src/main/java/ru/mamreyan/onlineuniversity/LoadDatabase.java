package ru.mamreyan.onlineuniversity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mamreyan.onlineuniversity.student.Student;
import ru.mamreyan.onlineuniversity.student.StudentRepository;

import java.util.Collection;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepository
    ) {
        return args -> {
            Iterable<Student> students = studentRepository.findAll();

            if (students instanceof Collection && ((Collection<Student>) students).size() <= 1) {

                students = studentRepository.findAll();
            }

            students.forEach(student -> log.info("Students:\n" + student));
        };
    }
}