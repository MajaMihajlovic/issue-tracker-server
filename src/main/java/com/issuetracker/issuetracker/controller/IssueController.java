package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.Project;
import com.issuetracker.issuetracker.repository.IssueRepository;
import com.issuetracker.issuetracker.repository.UserRepository;
import microsoft.exchange.webservices.data.core.request.GetEventsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Controller
@RequestMapping("api/issue")
@Scope("request")
public class IssueController extends GenericController<Issue,Integer> {


    @PersistenceContext
    private EntityManager entityManager;

    IssueRepository repository;

    @Autowired
    public IssueController(IssueRepository repo) {
        super(repo);
        repository = repo;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    String insertProject(@RequestBody Issue issue) {
    System.out.println(issue);
    return "";
    }

}
