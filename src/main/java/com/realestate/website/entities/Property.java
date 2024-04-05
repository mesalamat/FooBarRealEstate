package com.realestate.website.entities;


import com.realestate.website.entities.attachment.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.*;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;


    private PropertyType type = PropertyType.APARTMENT;

    private int bedRooms;
    private int bathRooms;
    private double area;
    private double price;
    private Unit unit;

    @ElementCollection(targetClass = UUID.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image", nullable = false)
    private List<UUID> images;

    public Property() {
    }
    public Property(String name){
        this();
        this.name = name;
    }
    public Property(String name, Unit unit){
        this();
        this.name = name;
        this.unit = unit;
    }
    public Property(String name, Unit unit, List<UUID> images){
        this();
        this.name = name;
        this.unit = unit;
        this.images = images;
    }


    @Getter(AccessLevel.PUBLIC)
    public enum Unit{

        SQUARE_METER("sqm"), SQUARE_FEET("sqft");


        private String abbreviation;

        Unit(String abbreviation){
            this.abbreviation = abbreviation;
        }

    }

    @Getter(AccessLevel.PUBLIC)
    public enum PropertyType{

        APARTMENT("Apartment"), HOUSE("House"), COMMERCIAL("Commercial");


        PropertyType(String name){
            this.name = name;
        }

        private String name;

    }

}
