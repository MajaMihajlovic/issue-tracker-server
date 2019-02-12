package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.common.exception.ForbiddenException;
import com.issuetracker.issuetracker.model.Project;
import com.issuetracker.issuetracker.model.ProjectHasUser;
import com.issuetracker.issuetracker.model.ProjectHasUserPK;
import com.issuetracker.issuetracker.repository.ProjectHasUserRepository;
import com.issuetracker.issuetracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/project-has-user")
@Scope("request")
public class ProjectHasUserController extends GenericController<ProjectHasUser,Integer>{

    private ProjectHasUserRepository repository;

    @Value("${badRequest.delete}")
    private String badRequestDelete;

    @Autowired
    public ProjectHasUserController( ProjectHasUserRepository repo) {
        super(repo);
        repository = repo;
    }


    @RequestMapping(value = {"/{id}/{projectId}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    String delete(@PathVariable Integer id,@PathVariable Integer projectId) throws BadRequestException{
        try {
            ProjectHasUser object = repository.findByProjectIdAndUserId(projectId,id);
            repository.delete(object);
            return "Success";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException(badRequestDelete);
        }
    }
}