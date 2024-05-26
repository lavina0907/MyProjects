package com.org.quizbuzz.repository;

import com.org.quizbuzz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM questiondb q WHERE q.category=:category ORDER BY RANDOM() LIMIT :noOfQues", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int noOfQues);
}
