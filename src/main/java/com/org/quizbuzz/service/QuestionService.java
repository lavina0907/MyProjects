package com.org.quizbuzz.service;

import com.org.quizbuzz.model.Question;
import com.org.quizbuzz.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionRepo.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        return new ResponseEntity<>(questionRepo.findByCategory(category),HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepo.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Question existingQuestion = questionRepo.findById(question.getId()).orElse(null);
        if(existingQuestion!=null) {
            existingQuestion.setId(question.getId());
            existingQuestion.setQuestionTitle(question.getQuestionTitle());
            existingQuestion.setCategory(question.getCategory());
            existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
            existingQuestion.setOption1(question.getOption1());
            existingQuestion.setOption2(question.getOption2());
            existingQuestion.setRightAnswer(question.getRightAnswer());
            questionRepo.save(existingQuestion );
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }

        return new ResponseEntity<>("Given id does not exist in database!", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if(questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
            return new ResponseEntity<>("Item Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("No such entity with given id", HttpStatus.NOT_FOUND);
    }
}
