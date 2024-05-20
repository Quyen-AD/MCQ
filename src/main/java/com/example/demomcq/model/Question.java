package com.example.demomcq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Entity
@Data
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int quesId;
	private String title;
	private String optionA;
	private String optionB;
	private String optionC;
	private String writeOption;
	private int ans;
	private int chose;
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "theme_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Theme theme;

	@ManyToOne
	@JoinColumn(name = "author")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User author;

	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "questions")
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private Set<Quiz> quizzes;
}
