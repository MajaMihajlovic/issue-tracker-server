package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Priority;
import com.issuetracker.issuetracker.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface PriorityRepository extends JpaRepository<Priority, Integer> {


}
