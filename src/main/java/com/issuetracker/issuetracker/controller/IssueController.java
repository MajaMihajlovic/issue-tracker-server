package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.model.Attachment;
import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.modelCustom.IssueAttachment;
import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;
import com.issuetracker.issuetracker.repository.AttachmentRepository;
import com.issuetracker.issuetracker.repository.IssueRepository;
import com.issuetracker.issuetracker.repository.UserRepository;
import com.issuetracker.issuetracker.util.EmailExecutorService;
import com.issuetracker.issuetracker.util.EmailSender;
import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
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
    AttachmentRepository attachmentRepository;

    @Value("${badRequest.insert}")
    private String badRequestInsert;
    @Autowired
    public IssueController(AttachmentRepository attachmentRepository, IssueRepository repo, UserRepository userRepository) {
        super(repo);
        repository = repo;
        this.userRepository=userRepository;
        this.attachmentRepository=attachmentRepository;
    }
    @RequestMapping(value="/getById/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    IssueAttachment getById(@PathVariable Integer id) {
        IssueAttachment issueAttachment=new IssueAttachment();
        IssueCustom issueCustom=repository.getCustomById(id);
        issueAttachment.setIssueCustom(issueCustom);
        List<Attachment> list=attachmentRepository.getByIssueId(id);
        issueAttachment.setList(list);
        return issueAttachment;
    }

    @RequestMapping(value="/getIssueById/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    Issue getIssueById(@PathVariable Integer id) {
        return repository.getById(id);
    }


    @RequestMapping(value="/getAll/",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssues() {
        return repository.getAllIssues();
    }

    @RequestMapping(value="/getAll/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssuesByUser(@PathVariable Integer id) {
       List<IssueCustom> lista=repository.getAllIssues().stream().filter(en->en.getAssigneId()==id).collect(Collectors.toList());
            return lista;
    }

    @RequestMapping(value="/getAllByProjectAndAssignee/{id}/{assigneeId}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssuesByProjectAndAssignee(@PathVariable Integer id, @PathVariable Integer assigneeId) {
        List<IssueCustom> lista=repository.getAllIssues().stream().filter(en->en.getProjectId()==id && en.getAssigneId()==assigneeId).collect(Collectors.toList());
        return lista;
    }

    @RequestMapping(value="/getAllByProject/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueCustom> getAllIssuesByProject(@PathVariable Integer id) {
        List<IssueCustom> lista=repository.getAllIssues().stream().filter(en->en.getProjectId()==id ).collect(Collectors.toList());
        return lista;
    }

    @RequestMapping(value="/byType/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueType> getIssueTypeByProject(@PathVariable Integer id) {
        List<IssueType> lista=repository.countIssueType(id);
        return lista;
    }

    @RequestMapping(value="/byState/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueType> getIssueTypeByState(@PathVariable Integer id) {
        List<IssueType> lista=repository.countIssueState(id);
        return lista;
    }

    @RequestMapping(value="/byPriority/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<IssueType> getIssuePriorityByProject(@PathVariable Integer id) {
        List<IssueType> lista=repository.countIssuePriority(id);
        return lista;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody String insertIssue(@RequestBody Issue issue) throws BadRequestException {
        if (repo.saveAndFlush(issue) != null) {
            EmailExecutorService.getEmailExecuto().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EmailSender.notify(userRepository.getUserById(issue.getAssigneeId()).getEmail(), "You have new task.\n" + issue.getTitle() + "\n" + issue.getDescription());

                    } catch (BadRequestException e) {
                        e.printStackTrace();
                    }
                }
            });
        return "Success";
        } else throw new BadRequestException(badRequestInsert);
    }

    @RequestMapping(value="/insertWithAttachment",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody String insertIssueWithAttachment(@RequestBody IssueAttachment issueAttachment) throws BadRequestException {
        Issue issue=issueAttachment.getIssue();
        Issue addedIssue;
        if(( addedIssue=repo.saveAndFlush(issue))!=null) {
            for (Attachment file : issueAttachment.getList()) {
                Attachment att = new Attachment();
                att.setFile(file.getFile());
                att.setName(file.getName());
                att.setIssueId(addedIssue.getId());
                attachmentRepository.saveAndFlush(att);
            }
            EmailExecutorService.getEmailExecuto().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EmailSender.notify(userRepository.getUserById(issue.getAssigneeId()).getEmail(), "You have new task.\n"+issue.getTitle()+"\n"+issue.getDescription());

                    } catch (BadRequestException e) {
                        e.printStackTrace();
                    }
                }
            });
            return "Success";
        }else throw new BadRequestException(badRequestInsert);

    }

    }




