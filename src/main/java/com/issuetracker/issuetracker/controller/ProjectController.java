package com.issuetracker.issuetracker.controller;

        import com.issuetracker.issuetracker.common.exception.BadRequestException;
        import com.issuetracker.issuetracker.common.exception.ForbiddenException;
        import com.issuetracker.issuetracker.model.*;
        import com.issuetracker.issuetracker.model.modelCustom.ProjectCustom;
        import com.issuetracker.issuetracker.repository.ProjectHasUserRepository;
        import com.issuetracker.issuetracker.repository.ProjectRepository;
        import com.issuetracker.issuetracker.repository.UserRepository;
        import com.issuetracker.issuetracker.util.EmailSender;
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
    private ProjectHasUserRepository projectHasUserRepo;

    @Value("${badRequest.insert}")
    private String badRequestInsert;

    @Value("${badRequest.update}")
    private String badRequestUpdate;

    @Value("${badRequest.noProject}")
    private String badRequestNoProject;

    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProjectController(ProjectRepository repo, ProjectHasUserRepository projectHasUserRepo) {
        super(repo);
        repository = repo;
        this.projectHasUserRepo=projectHasUserRepo;
    }

    @RequestMapping(value="/insert",method = RequestMethod.POST)
    @Transactional
    public @ResponseBody String insertProject(@RequestBody ProjectCustom project) throws BadRequestException {
        Project newProject=new Project();
        newProject.setDescription(project.getProject().getDescription());
        newProject.setFinnished((byte)0);
        newProject.setName(project.getProject().getName());
        newProject.setPhotoUrl(project.getProject().getPhotoUrl());
        Project pro=null;
        if(( pro=repo.saveAndFlush(newProject))!=null) {
            for(ParticipantInfo info:project.getList()){
                ProjectHasUser entity=new ProjectHasUser();
                entity.setProjectId(pro.getId());
                entity.setUserId(info.getId());
                projectHasUserRepo.saveAndFlush(entity);
                EmailSender.sendNotification(info.getText(),pro.getName());
            }
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


    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
    public @ResponseBody
    List<Project> getAllByName(@PathVariable("name") String name) throws BadRequestException {
        List<Project> projects = repository.getAllByNameContaining(name).stream().filter(en->en.getFinnished()==(byte)0).collect(Collectors.toList());
        if (projects != null ) {
            return projects;
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

    @Override
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        try {
            Project project = repo.findById(id).orElse(null);
            List<ProjectHasUser> usrs=projectHasUserRepo.findAllByProjectId(id);
            projectHasUserRepo.deleteAll(usrs);
            repo.deleteById(id);
            return "Success";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException(badRequestDelete);
        }
    }

    @Transactional
    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String update(@PathVariable Integer id){
            Project projectTemp = repository.findById(id).orElse(null);
            projectTemp.setFinnished((byte)1);
            repo.saveAndFlush(projectTemp);
            return "Success";
    }
}
