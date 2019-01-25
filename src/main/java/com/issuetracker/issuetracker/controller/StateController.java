package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.State;
import com.issuetracker.issuetracker.model.Type;
import com.issuetracker.issuetracker.repository.StateRepository;
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
@RequestMapping("api/state")
@Scope("request")
public class StateController extends GenericController<State,Integer> {


    @PersistenceContext
    private EntityManager entityManager;

    StateRepository repository;

    @Autowired
    public StateController(StateRepository repo) {
        super(repo);
        repository = repo;
    }


}
