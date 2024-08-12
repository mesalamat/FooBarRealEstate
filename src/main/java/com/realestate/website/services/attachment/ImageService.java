package com.realestate.website.services.attachment;


import com.realestate.website.entities.attachment.Image;
import com.realestate.website.entities.attachment.ImageModelAssembler;
import com.realestate.website.repositories.attachment.ImageRepository;
import com.realestate.website.util.ImageUtil;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {


    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageModelAssembler assembler;


    private final String uploadDirectory = "src/main/resources/static/userImages/";
    private final Path UPLOAD_PATH = Path.of(uploadDirectory);


    @SneakyThrows
    public ResponseEntity<Image> saveImageToStorage(MultipartFile image){
        UUID imageUUID = UUID.randomUUID();
        String fileName = imageUUID.toString() + "_" + image.getName();
        Path filePath = UPLOAD_PATH.resolve(fileName);
        if(!UPLOAD_PATH.toFile().exists()){
            Files.createDirectories(UPLOAD_PATH);
        }

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        EntityModel<Image> responseModel = assembler.toModel(repository.save(Image.builder().id(imageUUID).fileName(fileName).type(image.getContentType()).build()));

        return ResponseEntity.created(responseModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(responseModel.getContent());
    }


    @SneakyThrows
    @Transactional
    public byte[] getImageData(String fileName) {
        Path imagePath = UPLOAD_PATH.resolve(fileName);
        if(imagePath.toFile().exists()){
            return Files.readAllBytes(imagePath);
        }else return null;
    }

}
