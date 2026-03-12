package com.lm2a.tacosonline.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Size(min=5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    private Date createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
