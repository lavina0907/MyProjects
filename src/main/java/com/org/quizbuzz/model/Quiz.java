package com.org.quizbuzz.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "quizdb")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    // This is not normalised way to handle many to many mapping. for a many to many mapping we should have a separate table.
    @ManyToMany
    private List<Question> questions;

}
