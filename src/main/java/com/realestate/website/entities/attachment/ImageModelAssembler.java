package com.realestate.website.entities.attachment;

import com.realestate.website.controller.attachment.ImageController;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImageModelAssembler implements RepresentationModelAssembler<Image, EntityModel<Image>> {
    @SneakyThrows
    @Override
    public EntityModel<Image> toModel(Image entity){
        return EntityModel.of(entity, WebMvcLinkBuilder.linkTo(methodOn(ImageController.class).getImageById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ImageController.class).all()).withRel("allImages"));
    }
}
