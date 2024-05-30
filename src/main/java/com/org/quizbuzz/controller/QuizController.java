package com.org.quizbuzz.controller;

import com.org.quizbuzz.enums.DurationType;
import com.org.quizbuzz.model.QuestionWrapper;
import com.org.quizbuzz.model.Response;
import com.org.quizbuzz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    //Always use constructor injection instead of field injection I have made changes in other classes for reference.
    @Autowired
    QuizService quizService;

    //It's a post call so it is a good practice to use request body instead of request param.
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam String title, @RequestParam int noOfQues, @RequestParam int duration, @RequestParam String durationType){

        return quizService.createQuiz(category,title,noOfQues, duration, DurationType.valueOf(durationType));

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){

        return quizService.getQuizQuestions(id);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> response){

        return quizService.submitQuiz(id,response);

    }
}
