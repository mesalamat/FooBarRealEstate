package com.realestate.website.entities;

import com.realestate.website.controller.PropertyController;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PropertyModelAssembler implements RepresentationModelAssembler<Property, EntityModel<Property>> {
    @SneakyThrows
    @Override
    public EntityModel<Property> toModel(Property entity){
        return EntityModel.of(entity, WebMvcLinkBuilder.linkTo(methodOn(PropertyController.class).getPropertyById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PropertyController.class).all()).withRel("allProperties"));
    }
}
