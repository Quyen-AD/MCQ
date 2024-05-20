package com.example.demomcq.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionForm {

	private List<Question> questions;
	private Integer time;
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
}
