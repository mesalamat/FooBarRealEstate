package com.realestate.website.repositories.attachment;

import com.realestate.website.entities.attachment.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {


}
