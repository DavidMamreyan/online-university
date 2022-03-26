package ru.mamreyan.onlineuniversity.group;

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
public class GroupController {
    private final GroupRepository     groupRepository;
    private final GroupModelAssembler assembler;

    GroupController(
            GroupRepository groupRepository,
            GroupModelAssembler assembler
    ) {
        this.groupRepository = groupRepository;
        this.assembler       = assembler;
    }

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    public GroupModelAssembler getAssembler() {
        return assembler;
    }

    @GetMapping ("/groups")
    CollectionModel<EntityModel<Group>> all() {
        List<EntityModel<Group>> groups = StreamSupport.stream(
                groupRepository.findAll().spliterator(),
                false
        ).map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(
                groups,
                linkTo(methodOn(GroupController.class).all()).withSelfRel()
        );
    }

    @GetMapping ("/groups/{id}")
    EntityModel<Group> one(
            @PathVariable
                    Long id
    ) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));

        return assembler.toModel(group);
    }

    @PostMapping ("/groups")
    ResponseEntity<?> newGroup(
            @RequestBody
                    Group newGroup
    ) {
        if (newGroup.isNotValid()) {
            throw new GroupNotValidException(newGroup);
        }

        EntityModel<Group> entityModel = assembler.toModel(groupRepository.save(newGroup));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping ("/groups/{id}")
    ResponseEntity<?> replaceGroup(
            @PathVariable
                    Long id,
            @RequestBody
                    Group newGroup
    ) {
        if (newGroup.isNotValid()) {
            throw new GroupNotValidException(newGroup);
        }

        Group updatedGroup = groupRepository.findById(id).map(group -> {
            group.setName(newGroup.getName());
            return groupRepository.save(group);
        }).orElseGet(() -> {
            newGroup.setId(id);
            return groupRepository.save(newGroup);
        });

        EntityModel<Group> entityModel = assembler.toModel(updatedGroup);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping ("/groups/{id}")
    ResponseEntity<?> deleteGroup(
            @PathVariable
                    Long id
    ) {
        return groupRepository.findById(id).map(group -> {
            groupRepository.delete(group);
            return ResponseEntity.ok().body("Group " + id + " was deleted");
        }).orElseThrow(() -> new GroupNotFoundException(id));
    }
}