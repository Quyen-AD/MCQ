package com.example.demomcq.repository;

import com.example.demomcq.model.Result;
import com.example.demomcq.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepo extends JpaRepository<Theme, Integer> {
	
}
