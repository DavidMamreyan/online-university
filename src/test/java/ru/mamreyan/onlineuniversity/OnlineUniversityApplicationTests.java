package ru.mamreyan.onlineuniversity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamreyan.onlineuniversity.group.GroupRepository;
import ru.mamreyan.onlineuniversity.student.Student;
import ru.mamreyan.onlineuniversity.student.StudentRepository;

@SpringBootTest
class OnlineUniversityApplicationTests {
    private static final Logger            log = LoggerFactory.getLogger(LoadDatabase.class);
    @Autowired private   StudentRepository studentRepository;
    @Autowired private   GroupRepository   groupRepository;

    @Test
    void contextLoads() {
        Iterable<Student> students = studentRepository.findAll();

        students.forEach(student -> log.info(student.toString()));
    }
}