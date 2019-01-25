package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Type;
import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.TypeRepository;
import com.issuetracker.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("api/type")
@Scope("request")
public class TypeController extends GenericController<Type,Integer> {


    @PersistenceContext
    private EntityManager entityManager;

    TypeRepository repository;

    @Autowired
    public TypeController(TypeRepository repo) {
        super(repo);
        repository = repo;
    }

}
