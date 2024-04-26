package com.example.demomcq.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Data
@Table(name = "theme")
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Integer status;

	@OneToMany(mappedBy = "theme")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Question> questions = new ArrayList<>();


}
