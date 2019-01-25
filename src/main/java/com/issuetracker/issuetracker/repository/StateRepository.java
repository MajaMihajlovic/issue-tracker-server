package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.State;
import com.issuetracker.issuetracker.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface StateRepository extends JpaRepository<State, Integer> {

}
