package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Type;
import com.issuetracker.issuetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface TypeRepository  extends JpaRepository<Type, Integer> {

}
