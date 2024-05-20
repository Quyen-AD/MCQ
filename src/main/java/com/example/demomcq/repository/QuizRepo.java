package com.example.demomcq.repository;

import com.example.demomcq.model.Question;
import com.example.demomcq.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {
    List<Quiz> findAllByStatus(Integer status);
}