package com.issuetracker.issuetracker.controller;


import com.issuetracker.issuetracker.controller.GenericController;
import com.issuetracker.issuetracker.model.Priority;
import com.issuetracker.issuetracker.model.Version;
import com.issuetracker.issuetracker.repository.PriorityRepository;
import com.issuetracker.issuetracker.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("api/version")
@Scope("request")
public class VersionController extends GenericController<Version,Integer> {


    @PersistenceContext
    private EntityManager entityManager;

    VersionRepository repository;

    @Autowired
    public VersionController(VersionRepository repo) {
        super(repo);
        repository = repo;
    }


}
