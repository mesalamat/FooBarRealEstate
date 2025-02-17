package com.realestate.website.repositories.attachment;

import com.realestate.website.services.attachment.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageLoader {

    @Autowired
    private ImageService service;

    private static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

}