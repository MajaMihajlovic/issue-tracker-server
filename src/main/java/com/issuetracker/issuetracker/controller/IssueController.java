package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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


    @RequestMapping(value="/getAll/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssuesByUser(@PathVariable Integer id) {
       List<IssueCustom> lista=repository.getAllIssues().stream().filter(en->en.getAssigneId()==id).collect(Collectors.toList());
            return lista;
    }

    @RequestMapping(value="/getAllByProject/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssuesByProject(@PathVariable Integer id) {
        List<IssueCustom> lista=repository.getAllIssues().stream().filter(en->en.getProjectId()==id).collect(Collectors.toList());
        return lista;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    String insertProject(@RequestBody Issue issue) {
    System.out.println(issue);
    return "";
    }

}
