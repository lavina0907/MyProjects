package com.org.quizbuzz.service;

import com.org.quizbuzz.model.Question;
import com.org.quizbuzz.model.QuestionWrapper;
import com.org.quizbuzz.model.Quiz;
import com.org.quizbuzz.model.Response;
import com.org.quizbuzz.repository.QuestionRepo;
import com.org.quizbuzz.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<String> createQuiz(String category, String title, int noOfQues) {

        List<Question> questions = questionRepo.findRandomQuestionByCategory(category,noOfQues);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepo.save(quiz);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {

        Optional<Quiz> quiz = quizRepo.findById(id);

        List<Question> questions = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappers = new ArrayList<>();

        for(Question q : questions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2());
            questionWrappers.add(qw);
        }
        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> response) {

        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int correct=0;
        int i=0;

        for(Response res : response){
            // How would you make sure that sequence of provided answers is same as sequence of questions?
            if(res.getSubmittedAnswer().equals(questions.get(i).getRightAnswer()))
                    correct++;

            i++;
        }
        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
