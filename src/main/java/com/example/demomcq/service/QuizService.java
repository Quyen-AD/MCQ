package com.example.demomcq.service;

import com.example.demomcq.model.Question;
import com.example.demomcq.model.QuestionForm;
import com.example.demomcq.model.Result;
import com.example.demomcq.repository.QuestionRepo;
import com.example.demomcq.repository.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	public QuestionForm getQuestions() {
		List<Question> allQues = qRepo.findAll();
		List<Question> qList = new ArrayList<Question>();
		
		Random random = new Random();
		
		for(int i=0; i<10; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		qForm.setQuestions(qList);
		
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
}
