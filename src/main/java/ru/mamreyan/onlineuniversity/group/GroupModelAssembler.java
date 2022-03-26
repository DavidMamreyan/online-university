package ru.mamreyan.onlineuniversity.group;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class GroupModelAssembler implements RepresentationModelAssembler<Group, EntityModel<Group>> {
    @NonNull
    @Override
    public EntityModel<Group> toModel(
            @NonNull
                    Group group
    ) {
        return EntityModel.of(
                group,
                linkTo(methodOn(GroupController.class).one(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).all()).withRel("groups")
        );
    }
}