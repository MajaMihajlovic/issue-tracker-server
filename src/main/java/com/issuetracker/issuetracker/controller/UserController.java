package com.issuetracker.issuetracker.controller;

import com.issuetracker.issuetracker.common.exception.ForbiddenException;
import com.issuetracker.issuetracker.model.LoginInfo;
import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@RequestMapping("api/user")
@Scope("request")
public class UserController extends GenericController<User,Integer> {

    private UserRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserController(UserRepository repo) {
        super(repo);
        repository = repo;
    }


    @Override
    public @ResponseBody
    List<User> getAll() {
        List<User> users = cloner.deepClone(repository.getAllByActive( (byte)1));
        for(User user : users){
            user.setPassword(null);
        }

        return users;
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginInfo userInformation) throws ForbiddenException {
        User user = repository.login(userInformation.getUsername(), userInformation.getPassword());
        if (user != null) {
            userBean.setLoggedIn(true);
            userBean.setUser(user);
            return user;
        }
        throw new ForbiddenException("Forbidden");
    }
}
