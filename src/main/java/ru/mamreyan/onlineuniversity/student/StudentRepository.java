package ru.mamreyan.onlineuniversity.student;

import org.springframework.data.repository.CrudRepository;
import ru.mamreyan.onlineuniversity.group.Group;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Iterable<Student> findByLastNameIgnoreCase(String lastName);

    Iterable<Student> findByFirstNameIgnoreCase(String firstName);

    Iterable<Student> findByMiddleNameIgnoreCase(String middleName);

    int countByGroup(Group group);
}