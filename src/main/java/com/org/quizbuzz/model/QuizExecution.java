package com.org.quizbuzz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "quiz_execution")
public class QuizExecution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer quizId;

  private Integer userId;

  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean isActive;


}
