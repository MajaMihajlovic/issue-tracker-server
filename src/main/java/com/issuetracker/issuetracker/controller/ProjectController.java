package com.issuetracker.issuetracker.controller;

        import com.issuetracker.issuetracker.common.exception.BadRequestException;
        import com.issuetracker.issuetracker.model.Project;
        import com.issuetracker.issuetracker.model.User;
        import com.issuetracker.issuetracker.repository.ProjectRepository;
        import com.issuetracker.issuetracker.repository.UserRepository;
        import com.issuetracker.issuetracker.util.Validator;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Scope;
        import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.*;

        import javax.persistence.EntityManager;
        import javax.persistence.PersistenceContext;
        import javax.transaction.Transactional;
        import java.util.List;
        import java.util.stream.Collectors;

@Controller
@RequestMapping("api/project")
@Scope("request")
public class ProjectController extends GenericController<Project,Integer>{

    private ProjectRepository repository;

    @Value("${badRequest.insert}")
    private String badRequestInsert;

    @Value("${badRequest.update}")
    private String badRequestUpdate;

    @Value("${badRequest.noProject}")
    private String badRequestNoProject;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProjectController(ProjectRepository repo) {
        super(repo);
        repository = repo;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody String insertProject(@RequestBody Project project) throws BadRequestException {

        Project newProject=new Project();
        newProject.setDescription(project.getDescription());
        newProject.setFinnished((byte)0);
        newProject.setName(project.getName());
        newProject.setPhotoUrl(project.getPhotoUrl());
        if(repo.saveAndFlush(newProject)!=null) {
            return "Success";
        }else throw new BadRequestException(badRequestInsert);
    }

    @Override
    public @ResponseBody
    List<Project> getAll() {
        return cloner.deepClone(repository.getAllByFinnishedEquals((byte)0));
    }
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Project findById(@PathVariable("id") Integer id) throws BadRequestException {
        Project project = repository.findById(id).orElse(null);
        if (project != null ) {
            return project;
        } else {
            throw new BadRequestException(badRequestNoProject);
        }
    }




    @Override
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    String update(@PathVariable Integer id, @RequestBody Project project) throws BadRequestException {
        if(project!=null){
            Project projectTemp = repository.findById(id).orElse(null);
            projectTemp.setName(project.getName());
            projectTemp.setDescription(project.getDescription());
            projectTemp.setFinnished(project.getFinnished());
            projectTemp.setPhotoUrl(project.getPhotoUrl());
            repo.saveAndFlush(projectTemp);
            return "Success";
        }throw new BadRequestException(badRequestUpdate);
    }

}
