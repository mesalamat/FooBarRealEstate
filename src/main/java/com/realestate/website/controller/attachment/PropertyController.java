package com.realestate.website.controller.attachment;

import com.realestate.website.entities.Property;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
public class PropertyController {


    @GetMapping("/addProperty")
    public ModelAndView addProperty(){
        ModelAndView model = new ModelAndView("addproperty");
        model.addObject("units", Property.Unit.values());
        return model;
    }

    //TODO:Move this into its own seperate Controller
    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("index");
        return model;
    }


}
