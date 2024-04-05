package com.realestate.website.repositories.attachment;

import com.realestate.website.entities.attachment.Image;
import com.realestate.website.services.attachment.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class ImageLoader {

    @Autowired
    private ImageService service;

    private static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

}