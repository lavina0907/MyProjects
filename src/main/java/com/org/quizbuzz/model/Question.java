package com.org.quizbuzz.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questiondb")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String category;
    private String difficultyLevel;
    private String rightAnswer;

}
