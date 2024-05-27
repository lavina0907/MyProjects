package com.org.quizbuzz.service;

import com.org.quizbuzz.model.Question;
import com.org.quizbuzz.repository.QuestionRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionService {

  private final QuestionRepo questionRepo;

  // Always check query return content and send response status accordingly like below. it is not a good practice to send 200 OK with empty content.
  public ResponseEntity<List<Question>> getAllQuestions() {
    List<Question> questions = questionRepo.findAll();
    if (questions.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
  }

  // Always check query return content and send response status accordingly like below. it is not a good practice to send 200 OK with empty content.
  public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
    return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
  }

  // Never use data model as request body. good practice is to use DTOs for request and response.
  public ResponseEntity<String> addQuestion(Question question) {
    questionRepo.save(question);
    // Always return response with proper body else you can ignore return any data it is just status code which just says that object is created successfully. and you have to handle unhappy path as well with proper status code.
    return new ResponseEntity<>("success", HttpStatus.CREATED);
  }

  public ResponseEntity<String> updateQuestion(Question question) {
    //No validation for the values in Question object?
    Question existingQuestion = questionRepo.findById(question.getId()).orElse(null);
    if (existingQuestion != null) {
      //if Question is Generative primary key why we need to update it?
      existingQuestion.setId(question.getId());
      existingQuestion.setQuestionTitle(question.getQuestionTitle());
      existingQuestion.setCategory(question.getCategory());
      existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
      existingQuestion.setOption1(question.getOption1());
      existingQuestion.setOption2(question.getOption2());
      existingQuestion.setRightAnswer(question.getRightAnswer());
      questionRepo.save(existingQuestion);
      return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    // Again it is not good practice to return simple string message. you can return proper response (JSON format) with proper status code.
    return new ResponseEntity<>("Given id does not exist in database!", HttpStatus.NOT_FOUND);

  }

  public ResponseEntity<String> deleteQuestion(Integer id) {
    if (questionRepo.existsById(id)) {
      questionRepo.deleteById(id);
      return new ResponseEntity<>("Item Deleted", HttpStatus.OK);
    }

    return new ResponseEntity<>("No such entity with given id", HttpStatus.NOT_FOUND);
  }
}
