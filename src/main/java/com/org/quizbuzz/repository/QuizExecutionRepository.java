package com.org.quizbuzz.repository;

import com.org.quizbuzz.model.QuizExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizExecutionRepository extends JpaRepository<QuizExecution, Integer> {

  QuizExecution findQuizExecutionByQuizIdAndUserIdAndActive(Integer quizId, Integer userId, Boolean active);

}
