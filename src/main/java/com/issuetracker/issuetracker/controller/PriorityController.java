package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Priority;
import com.issuetracker.issuetracker.model.Type;
import com.issuetracker.issuetracker.repository.PriorityRepository;
import com.issuetracker.issuetracker.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("api/priority")
@Scope("request")
public class PriorityController extends GenericController<Priority,Integer> {


    @PersistenceContext
    private EntityManager entityManager;

    PriorityRepository repository;

    @Autowired
    public PriorityController(PriorityRepository repo) {
        super(repo);
        repository = repo;
    }


}
