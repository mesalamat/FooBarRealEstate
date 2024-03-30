package com.realestate.website.entities;


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


    private int bedRooms;
    private int bathRooms;
    private double area;
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


    @Getter(AccessLevel.PUBLIC)
    public enum Unit{

        SQUARE_METER("sqm"), SQUARE_FEET("sqft");


        private String abbreviation;

        Unit(String abbreviation){
            this.abbreviation = abbreviation;
        }

    }

}
