package com.org.quizbuzz.repository;

import com.org.quizbuzz.model.Question;
import com.org.quizbuzz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {


}
