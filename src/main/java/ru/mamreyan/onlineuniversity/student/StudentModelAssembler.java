package ru.mamreyan.onlineuniversity.student;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class StudentModelAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {
    @NonNull
    @Override
    public EntityModel<Student> toModel(
            @NonNull
                    Student student
    ) {
        return EntityModel.of(
                student,
                linkTo(methodOn(StudentController.class).one(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).all()).withRel("students")
        );
    }
}