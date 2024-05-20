package com.example.demomcq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Entity
@Data
@Table(name = "quiz")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer quizId;

	private String title;
	private Integer time;
	private LocalDateTime date;

	private Integer status;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JoinTable(name = "quiz_question",
			joinColumns = @JoinColumn(name="quiz_id"),
			inverseJoinColumns = @JoinColumn(name="question_id"))
	private List<Question> questions;

	@Transient
	private List<Integer> questionIds;
}
