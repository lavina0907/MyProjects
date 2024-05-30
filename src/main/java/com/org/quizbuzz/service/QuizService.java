package com.org.quizbuzz.service;

import static com.org.quizbuzz.utils.QuizExecutionUtils.calculateEndTime;

import com.org.quizbuzz.enums.DurationType;
import com.org.quizbuzz.model.Question;
import com.org.quizbuzz.model.QuestionWrapper;
import com.org.quizbuzz.model.Quiz;
import com.org.quizbuzz.model.QuizExecution;
import com.org.quizbuzz.model.Response;
import com.org.quizbuzz.repository.QuestionRepo;
import com.org.quizbuzz.repository.QuizExecutionRepository;
import com.org.quizbuzz.repository.QuizRepo;
import com.org.quizbuzz.repository.UserRepository;
import java.time.LocalDateTime;
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

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuizExecutionRepository quizExecutionRepository;

    public ResponseEntity<String> createQuiz(String category, String title, int noOfQues, int duration, DurationType durationType) {

        List<Question> questions = questionRepo.findRandomQuestionByCategory(category,noOfQues);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quiz.setDuration(duration);
        quiz.setDurationType(durationType.name());

        quizRepo.save(quiz);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {

        Optional<Quiz> quiz = quizRepo.findById(id);
        QuizExecution quizExecution = quizExecutionRepository.findQuizExecutionByQuizIdAndUserIdAndActive(id,1,true);
        if(quizExecution !=null ){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        List<Question> questions = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappers = new ArrayList<>();

        for(Question q : questions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2());
            questionWrappers.add(qw);
        }
        createQuizExecution(id,1);

        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    private void createQuizExecution(int quizId, int userId) {
        QuizExecution quizExecution = new QuizExecution();
        quizExecution.setQuizId(quizId);
        quizExecution.setUserId(userId);
        quizExecution.setActive(true);
        quizExecution.setStartTime(LocalDateTime.now());
        quizExecutionRepository.save(quizExecution);

    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> response) {
        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int correct=0;
        int i=0;
        QuizExecution quizExecution = quizExecutionRepository.findQuizExecutionByQuizIdAndUserIdAndActive(id,1,true);
        LocalDateTime startTime = quizExecution.getStartTime();
        LocalDateTime calculateEndTime = calculateEndTime(startTime, quiz.getDuration(),DurationType.valueOf(quiz.getDurationType()));
        if(calculateEndTime.isBefore(LocalDateTime.now())) {
          updateQuizExecution(quizExecution);
          return new ResponseEntity<>(-1, HttpStatus.OK);
        }
        for(Response res : response){
            // How would you make sure that sequence of provided answers is same as sequence of questions?
            if(res.getSubmittedAnswer().equals(questions.get(i).getRightAnswer()))
                    correct++;

            i++;
        }
      updateQuizExecution(quizExecution);
      return new ResponseEntity<>(correct, HttpStatus.OK);
    }

  private void updateQuizExecution(QuizExecution quizExecution) {
    quizExecution.setEndTime(LocalDateTime.now());
    quizExecution.setActive(false);
    quizExecutionRepository.save(quizExecution);
  }
}
