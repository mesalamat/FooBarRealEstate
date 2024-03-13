package com.realestate.website.services.attachment;


import com.realestate.website.entities.attachment.Image;
import com.realestate.website.entities.attachment.ImageModelAssembler;
import com.realestate.website.repositories.attachment.ImageRepository;
import com.realestate.website.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {


    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageModelAssembler assembler;


    public ResponseEntity<Image> uploadImage(MultipartFile multipartFile) throws IOException {
        Image existing;
        if((existing = repository.findAll().stream().filter(image -> {
            try {
                return Arrays.equals(image.getImageData(), ImageUtil.compressImage(multipartFile.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).findAny().orElse(null)) != null){
            EntityModel<Image> responseModel = assembler.toModel(existing);
            return ResponseEntity.ok(responseModel.getContent());
        }
        EntityModel<Image> responseModel = assembler.toModel(repository.save(Image.builder().id(UUID.randomUUID()).type(multipartFile.getContentType()).imageData(ImageUtil.compressImage(multipartFile.getBytes())).build()));
        return ResponseEntity.created(responseModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(responseModel.getContent());
    }

    @Transactional
    public byte[] getImageData(UUID name) {
        Optional<Image> dbImage = repository.findById(name);
        return ImageUtil.decompressImage(dbImage.get().getImageData());
    }

}
