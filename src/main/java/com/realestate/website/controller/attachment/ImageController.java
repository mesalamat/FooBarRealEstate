package com.realestate.website.controller.attachment;

import com.realestate.website.entities.attachment.Image;
import com.realestate.website.entities.attachment.ImageModelAssembler;
import com.realestate.website.repositories.attachment.ImageRepository;
import com.realestate.website.services.attachment.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ImageController {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageModelAssembler assembler;

    @Autowired
    private ImageService service;


    @GetMapping("/imageUpload")
    public ModelAndView imageUpload(){
        ModelAndView model = new ModelAndView("upload");
        model.addObject("message", "Aloha");
        return model;
    }


    @GetMapping("/uploads/image/{id}")
    public EntityModel<Image> getImageById(@PathVariable UUID id) throws IOException {
        Image stats = repository.findAll().stream().filter(stats1 -> stats1.getId().equals(id)).findAny().orElse(null);
        return assembler.toModel(stats);
    }
    
    @DeleteMapping("/uploads/image/{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable UUID id) throws IOException {
        Image stats = repository.findAll().stream().filter(stats1 -> stats1.getId().equals(id)).findAny().orElse(null);
        repository.deleteById(stats.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/uploads/images")

    public CollectionModel<EntityModel<Image>> all(){
        List<EntityModel<Image>> allStats = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(allStats, linkTo(methodOn(ImageController.class).all()).withSelfRel());
    }

    @PostMapping("/uploads/images")
    public ResponseEntity<Image> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        return service.saveImageToStorage(file);
    }



    @GetMapping("/uploads/image/dataByUUID/{uuid}")
    public void showProductImage(@PathVariable UUID uuid,
                                 HttpServletResponse response) throws IOException {

        Optional<Image> image = repository.findById(uuid);
        if(!image.isPresent())return;

        InputStream is = new ByteArrayInputStream(service.getImageData(image.get().getFileName()));
        IOUtils.copy(is, response.getOutputStream());
    }
    @GetMapping("/uploads/image/data/{fileName}")
    public void showProductImage(@PathVariable String fileName,
                                 HttpServletResponse response) throws IOException {
        InputStream is = new ByteArrayInputStream(service.getImageData(fileName));
        IOUtils.copy(is, response.getOutputStream());
    }

}
