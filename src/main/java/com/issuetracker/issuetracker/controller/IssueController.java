package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;
import com.issuetracker.issuetracker.repository.IssueRepository;
import com.issuetracker.issuetracker.repository.UserRepository;
import com.issuetracker.issuetracker.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    UserRepository userRepository;
    IssueRepository repository;

    @Value("${badRequest.insert}")
    private String badRequestInsert;
    @Autowired
    public IssueController(IssueRepository repo, UserRepository userRepository) {
        super(repo);
        repository = repo;
        this.userRepository=userRepository;
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

    @RequestMapping(value="/byType/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueType> getIssueTypeByProject(@PathVariable Integer id) {
        List<IssueType> lista=repository.countIssueType(id);
        return lista;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    String insertProject(@RequestBody Issue issue) throws BadRequestException {
    if(repo.saveAndFlush(issue)!=null){
        EmailSender.notify(userRepository.getUserById(issue.getAssigneeId()).getEmail(), "You have new task.\n"+issue.getTitle()+"\n"+issue.getDescription());
        return "Success";
    }else throw new BadRequestException(badRequestInsert);

    }





}
