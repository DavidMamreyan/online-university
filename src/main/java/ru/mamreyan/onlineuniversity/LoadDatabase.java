package ru.mamreyan.onlineuniversity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mamreyan.onlineuniversity.group.Group;
import ru.mamreyan.onlineuniversity.group.GroupRepository;
import ru.mamreyan.onlineuniversity.student.Sex;
import ru.mamreyan.onlineuniversity.student.Student;
import ru.mamreyan.onlineuniversity.student.StudentRepository;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

@Configuration
public class LoadDatabase {
    // private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    //
    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepository,
            GroupRepository groupRepository
    ) {
        return args -> {
            Iterable<Student> students = studentRepository.findAll();

            if (students instanceof Collection && ((Collection<Student>) students).size() < 1) {
                studentRepository.save(new Student.StudentBuilder()
                                               .lastName("Иванов")
                                               .firstName("Иван")
                                               .middleName("Иванович")
                                               .sex(Sex.MALE)
                                               .birthDate(new GregorianCalendar(
                                                       2000,
                                                       Calendar.JANUARY,
                                                       1
                                               ))
                                               .entryDate(new GregorianCalendar(
                                                       2020,
                                                       Calendar.SEPTEMBER,
                                                       1
                                               ))
                                               .group(groupRepository.save(new Group("2020-1-1")))
                                               .build());
                //
                // students = studentRepository.findAll();
            }
            //
            // students.forEach(student -> log.info(student.toString()));
        };
    }
}