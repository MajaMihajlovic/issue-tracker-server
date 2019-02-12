package com.issuetracker.issuetracker.controller;

import com.google.common.io.ByteStreams;
import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.common.exception.ForbiddenException;
import com.issuetracker.issuetracker.model.LoginInfo;
import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.model.modelCustom.UserCustom;
import com.issuetracker.issuetracker.repository.UserRepository;
import com.issuetracker.issuetracker.util.*;
import org.apache.catalina.connector.CoyoteInputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("api/user")
@Scope("request")
public class UserController extends GenericController<User,Integer> {

    private UserRepository repository;
    @Value("${badRequest.noUser}")
    private String badRequestNoUser;

    @Value("${badRequest.insert}")
    private String badRequestInsert;

    @Value("${badRequest.update}")
    private String badRequestUpdate;

    @Value("${badRequest.delete}")
    private String badRequestDelete;

    @Value("${badRequest.registration}")
    private String badRequestRegistration;

    @Value("${badRequest.usernameExists}")
    private String badRequestUsernameExists;

    @Value("${badRequest.validateEmail}")
    private String badRequestValidateEmail;

    @Value("${badRequest.oldPassword}")
    private String badRequestOldPassword;

    @Value("${badRequest.emailExists}")
    private String badRequestEmailExists;

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
        List<User> users = repository.getAllByActive( (byte)1);
        List<User> copy=users;
        for(User user : copy){
            user.setPassword(null);
        }

        return copy;
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public @ResponseBody
    List<UserCustom> getUserFullName() {
        List<UserCustom> users = repository.getAllFiltered();
        System.out.println(users);
        return users;
    }

    @RequestMapping(value = "/getParticipants/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<UserCustom> getUserFullName(@PathVariable Integer id) {
        List<UserCustom> users = repository.getParticipants(id);
        return users;
    }

    @RequestMapping(value = "/getNonParticipants/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<UserCustom> getNonParticipants(@PathVariable Integer id) {
        List<UserCustom> users = repository.getNonParticipants(id);
        return users;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody  User login(@RequestBody LoginInfo userInformation) throws ForbiddenException {
        User user = repository.login(userInformation.getUsername(), userInformation.getPassword());
        if (user != null) {
            user.setPassword(null);
            System.out.println(user.getPhoto());
            return user;
        }
        throw new ForbiddenException("Forbidden");
    }

    @SuppressWarnings("SameReturnValue")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody
    String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "Success";
    }

    @RequestMapping(value="/insert", method = RequestMethod.POST)
    public @ResponseBody String insert(String email, byte[] file, String password, String username, String fullname) throws BadRequestException, IOException {
        User user=new User();
        user.setUsername(username);
        user.setFullName(fullname);
        user.setPassword(Util.hashPassword(password));
        user.setEmail(email);
        user.setPhoto(file);
        user.setActive((byte)1);
        if(repository.countAllByEmail(user.getEmail()).compareTo(0) == 0){
            if(Validator.validateEmail(user.getEmail())){
                String randomToken = Util.randomString(16);
                if(repo.saveAndFlush(user) != null){
                    //EmailSender.sendRegistrationLink(user.getEmail().trim(), randomToken);
                    return "Success";
                }
                throw new BadRequestException(badRequestInsert);
            }
            throw new BadRequestException(badRequestValidateEmail);
        }
        throw new BadRequestException(badRequestEmailExists);
    }
    @SuppressWarnings("SameReturnValue")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public @ResponseBody
    String registration(@RequestBody User newUser) throws BadRequestException {
        System.out.println("New userrrr:"+newUser);
        User userWithUsername = repository.getByUsername(newUser.getUsername());
        System.out.println(userWithUsername);
        if(userWithUsername == null) {
             //   if (Validator.passwordChecking(newUser.getPassword())) {
            if(repository.countAllByEmail(newUser.getEmail()).compareTo(0) == 0){
                if(Validator.validateEmail(newUser.getEmail())){
                       // User user = entityManager.find(User.class, newUser.getId());
                        User user=new User();
                        user.setUsername(newUser.getUsername());
                        user.setPassword(Util.hashPassword(newUser.getPassword()));
                        user.setFullName(newUser.getFullName());
                       // user.setPhoto(newUser.getPhoto());
                        user.setActive((byte) 1);
                        user.setEmail(newUser.getEmail());
                        if (repo.saveAndFlush(user) != null) {
                            return "Success";
                        }
                        throw new BadRequestException(badRequestRegistration);
                    }
                throw new BadRequestException(badRequestValidateEmail);
            }
            throw new BadRequestException(badRequestEmailExists);
              //  }
              //  throw new BadRequestException(badRequestPasswordStrength);
            }throw  new BadRequestException(badRequestUsernameExists);

    }


    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody
    String updatePassword(@RequestBody PasswordInformation passwordInformation) throws BadRequestException{
        User user = repository.findById(passwordInformation.getId()).orElse(null);
        System.out.println(passwordInformation);
        if(user != null){
            if(passwordInformation.getOldPassword() != null && user.getPassword().trim().equals(Util.hashPassword(passwordInformation.getOldPassword().trim()))) {
                    user.setPassword(Util.hashPassword(passwordInformation.getNewPassword()));
                    if (repo.saveAndFlush(user) != null) {
                        return "Success";
                    }
                    throw new BadRequestException(badRequestUpdate);
                }
            throw new BadRequestException(badRequestOldPassword);
        }
        throw new BadRequestException(badRequestNoUser);
    }

    @RequestMapping(value = "/registration/{token}", method = RequestMethod.GET)
    public @ResponseBody
    User requestForRegistration(@PathVariable String token) {
        User user = repository.getByToken(token);
        if (user == null) {
            return null;
        }

        if (new Timestamp(System.currentTimeMillis()).before(new Timestamp(user.getTokenTime().getTime() + 10 * 60 * 1000))) {
            return user;
        } else {
            return null;
        }
    }
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    User findById(@PathVariable("id") Integer id) throws BadRequestException {
        User user = repository.findById(id).orElse(null);
        if (user != null ) {
            user.setPassword(null);
            return user;
        } else {
            throw new BadRequestException(badRequestNoUser);
        }
    }

    @Override
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    String update(@PathVariable Integer id, @RequestBody User user) {
                        User userTemp = repository.findById(id).orElse(null);
                        userTemp.setFullName(user.getFullName());
                        userTemp.setEmail(user.getEmail());
                        userTemp.setUsername(user.getUsername());
                repo.saveAndFlush(userTemp);
        return "Success"; }
}
