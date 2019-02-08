package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Attachment;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;
import com.issuetracker.issuetracker.repository.AttachmentRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("api/attachment")
@Scope("request")
public class AttachmentController extends GenericController<Attachment, Integer> {

    AttachmentRepository repo;

    public AttachmentController(AttachmentRepository repo) {
        super(repo);
        this.repo=repo;
    }

    @RequestMapping(value="/getById/{id}",method = RequestMethod.GET)
    @Transactional
    public @ResponseBody
    List<Attachment> getIssueTypeByProject(@PathVariable Integer id) {
        System.out.println(id);
        if(id!=null) {
            return repo.getByIssueId(id);

        }else return null;
    }

}
