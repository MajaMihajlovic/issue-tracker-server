package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.model.Attachment;
import com.issuetracker.issuetracker.repository.AttachmentRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/attachment")
@Scope("request")
public class AttachmentController extends GenericController<Attachment, Integer> {

    AttachmentRepository repo;

    public AttachmentController(AttachmentRepository repo) {
        super(repo);
    }

}
