package com.example.demomcq.service;

import com.example.demomcq.model.Question;
import com.example.demomcq.model.QuestionForm;
import com.example.demomcq.model.Quiz;
import com.example.demomcq.model.Result;
import com.example.demomcq.repository.QuestionRepo;
import com.example.demomcq.repository.QuizRepo;
import com.example.demomcq.repository.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class QuizService {
	
	@Autowired
	Question question;
	@Autowired
	QuestionForm qForm;
	@Autowired
	QuestionRepo qRepo;
	@Autowired
	Result result;
	@Autowired
	ResultRepo rRepo;
	@Autowired
	QuizRepo quizRepo;

	public List<Quiz> getAll() {
		return quizRepo.findAll();
	}
	
	public QuestionForm getQuestions(Quiz quiz) {
		List<Question> allQues = quiz.getQuestions();
		qForm.setQuestions(allQues);
		qForm.setTime(quiz.getTime());
		return qForm;
	}

	public int getResult(QuestionForm qForm) {
		int correct = 0;

		for (Question q : qForm.getQuestions()) {
			if (q.getAns() == 4) {
				if (q.getWriteOption() != null && q.getWriteOption().equals(String.valueOf(q.getChose()))) {
					correct++;
				}
			} else {
				if (q.getAns() == q.getChose()) {
					correct++;
				}
			}
		}

		return correct;
	}


	public void saveScore(Result result) {
		rRepo.save(result);
	}
	
	public List<Result> getTopScore() {
		List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
		return sList;
	}

	public Quiz findById(Integer id) {
		return quizRepo.findById(id).get();
	}
}
