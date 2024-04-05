package com.realestate.website.controller.attachment;

import com.realestate.website.entities.Property;
import com.realestate.website.entities.PropertyModelAssembler;
import com.realestate.website.entities.attachment.Image;
import com.realestate.website.repositories.PropertyLoader;
import com.realestate.website.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PropertyController {


    @Autowired
    private PropertyModelAssembler assembler;
    @Autowired
    private PropertyRepository repository;



    @GetMapping("/addProperty")
    public ModelAndView addProperty(){
        ModelAndView model = new ModelAndView("addproperty");
        model.addObject("units", Property.Unit.values());
        model.addObject("types", Property.PropertyType.values());
        return model;
    }

    @GetMapping("/properties")
    public ModelAndView properties(){
        ModelAndView model = new ModelAndView("properties");
        model.addObject("properties", repository.findAll());
        return model;
    }

    @PostMapping("/api/properties")
    ResponseEntity<?> newPlayerStats(@RequestBody Property property){
        EntityModel<Property> responseModel = assembler.toModel(repository.save(property));
        return ResponseEntity.created(responseModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(responseModel);
    }


    @GetMapping("/api/properties")
    public CollectionModel<EntityModel<Property>> all(){
        List<EntityModel<Property>> allStats = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(allStats, linkTo(methodOn(PropertyController.class).all()).withSelfRel());
    }

    @GetMapping("/api/property/{id}")
    public EntityModel<Property> getPropertyById(@PathVariable UUID id){
        Property p = repository.findById(id).get();
        return assembler.toModel(p);
    }

    //TODO:Move this into its own seperate Controller
    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("index");
        return model;
    }


}
