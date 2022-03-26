package ru.mamreyan.onlineuniversity.student;

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
public class StudentController {
    private final StudentRepository     studentRepository;
    private final StudentModelAssembler assembler;

    StudentController(
            StudentRepository studentRepository,
            StudentModelAssembler assembler
    ) {
        this.studentRepository = studentRepository;
        this.assembler         = assembler;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public StudentModelAssembler getAssembler() {
        return assembler;
    }

    @GetMapping ("/students")
    CollectionModel<EntityModel<Student>> all() {
        List<EntityModel<Student>> students = StreamSupport.stream(
                studentRepository.findAll().spliterator(),
                false
        ).map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                students,
                linkTo(methodOn(StudentController.class).all()).withSelfRel()
        );
    }

    @GetMapping ("/students/{id}")
    EntityModel<Student> one(
            @PathVariable
                    Long id
    ) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

        return assembler.toModel(student);
    }

    @PostMapping ("/students")
    ResponseEntity<?> newStudent(
            @RequestBody
                    Student newStudent
    ) {
        if (newStudent.isNotValid()) {
            throw new StudentNotValidException(newStudent);
        }

        EntityModel<Student> entityModel = assembler.toModel(studentRepository.save(newStudent));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping ("/students/{id}")
    ResponseEntity<?> replaceStudent(
            @PathVariable
                    Long id,
            @RequestBody
                    Student newStudent
    ) {
        if (newStudent.isNotValid()) {
            throw new StudentNotValidException(newStudent);
        }

        Student updatedStudent = studentRepository.findById(id).map(student -> {
            student.setLastName(newStudent.getLastName());
            student.setFirstName(newStudent.getFirstName());
            student.setMiddleName(newStudent.getMiddleName());
            student.setSex(newStudent.getSex());
            student.setBirthDate(newStudent.getBirthDate());
            student.setEntryDate(newStudent.getEntryDate());
            student.setGroup(newStudent.getGroup());
            return studentRepository.save(student);
        }).orElseGet(() -> {
            newStudent.setId(id);
            return studentRepository.save(newStudent);
        });

        EntityModel<Student> entityModel = assembler.toModel(updatedStudent);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping ("/students/{id}")
    ResponseEntity<?> deleteStudent(
            @PathVariable
                    Long id
    ) {
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.ok().body("Student " + id + " was deleted");
        }).orElseThrow(() -> new StudentNotFoundException(id));
    }
}