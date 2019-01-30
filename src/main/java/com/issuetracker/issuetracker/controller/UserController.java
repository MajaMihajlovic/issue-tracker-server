package com.issuetracker.issuetracker.controller;

import com.google.common.io.ByteStreams;
import com.issuetracker.issuetracker.common.exception.BadRequestException;
import com.issuetracker.issuetracker.common.exception.ForbiddenException;
import com.issuetracker.issuetracker.model.LoginInfo;
import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.model.modelCustom.UserCustom;
import com.issuetracker.issuetracker.repository.UserRepository;
import com.issuetracker.issuetracker.util.EmailSender;
import com.issuetracker.issuetracker.util.PasswordInformation;
import com.issuetracker.issuetracker.util.Util;
import com.issuetracker.issuetracker.util.Validator;
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

    @Value("${badRequest.stringMaxLength}")
    private String badRequestStringMaxLength;

    @Value("${badRequest.binaryLength}")
    private String badRequestBinaryLength;

    @Value("${badRequest.registration}")
    private String badRequestRegistration;

    @Value("${badRequest.usernameExists}")
    private String badRequestUsernameExists;

    @Value("${badRequest.passwordStrength}")
    private String badRequestPasswordStrength;


    @Value("${badRequest.validateEmail}")
    private String badRequestValidateEmail;

    @Value("${badRequest.oldPassword}")
    private String badRequestOldPassword;

    @Value("${badRequest.repeatedNewPassword}")
    private String badRequestRepeatedNewPassword;

    @Value("${badRequest.resetPassword}")
    private String badRequestResetPassword;

    @Value("${badRequest.deactivateUser}")
    private String badRequestDeactivateUser;

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
        List<User> users = cloner.deepClone(repository.getAllByActive( (byte)1));
        for(User user : users){
            user.setPassword(null);
        }

        return users;
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



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody  User login(@RequestBody LoginInfo userInformation) throws ForbiddenException {
        User user = repository.login(userInformation.getUsername(), userInformation.getPassword());
        if (user != null) {
            user.setPassword(null);
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
    public @ResponseBody String insert(String email, InputStream file, String password, String username, String fullname) throws BadRequestException, IOException {
        User user=new User();
        user.setUsername(username);
        user.setFullName(fullname);
        user.setPassword(Util.hashPassword(password));
        user.setEmail(email);

        user.setPhoto(file.readAllBytes());

        user.setActive((byte)1);
        System.out.println("Insert"+user);
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
                    if (Validator.stringMaxLength(newUser.getFullName(), 100)) {
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
                    throw new BadRequestException(badRequestStringMaxLength.replace("{tekst}", "imena").replace("{broj}", String.valueOf(100)));
                }
                throw new BadRequestException(badRequestValidateEmail);
            }
            throw new BadRequestException(badRequestEmailExists);
              //  }
              //  throw new BadRequestException(badRequestPasswordStrength);
            }throw  new BadRequestException(badRequestUsernameExists);

    }
    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String deactivate(@PathVariable Integer id) throws BadRequestException {
        User user = repository.findById(id).orElse(null);
        if(user != null){
            user.setActive((byte)0);
            if(repo.saveAndFlush(user) != null){
                return "Success";
            }
            throw new BadRequestException(badRequestDeactivateUser);
        }
        throw new BadRequestException(badRequestNoUser);
    }
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public @ResponseBody
    String resetPassword(@RequestBody LoginInfo userInformation) throws BadRequestException {
        User userTemp = repository.getByUsername(userInformation.getUsername());
        if (userTemp != null) {
                User user = repository.findById(userTemp.getId()).orElse(null);
                String newPassword = Util.randomString(16);
            user.setPassword(Util.hashPassword(newPassword));
                if(repo.saveAndFlush(user) != null){
                    EmailSender.sendNewPassword(user.getEmail().trim(), newPassword);

                    return "Success";
                }
                throw new BadRequestException(badRequestResetPassword);
            }
            throw new BadRequestException(badRequestNoUser);
    }
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody
    String updatePassword(@RequestBody PasswordInformation passwordInformation) throws BadRequestException{
        User user = repository.findById(passwordInformation.getId()).orElse(null);
        System.out.println(passwordInformation);
        if(user != null){
            if(passwordInformation.getOldPassword() != null && user.getPassword().trim().equals(Util.hashPassword(passwordInformation.getOldPassword().trim()))) {
                if (passwordInformation.getRepeatedNewPassword() != null && passwordInformation.getNewPassword().trim().equals(passwordInformation.getRepeatedNewPassword().trim())) {
                    user.setPassword(Util.hashPassword(passwordInformation.getNewPassword()));
                    if (repo.saveAndFlush(user) != null) {
                        return "Success";
                    }
                    throw new BadRequestException(badRequestUpdate);
                }
                throw new BadRequestException(badRequestRepeatedNewPassword);
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
    String update(@PathVariable Integer id, @RequestBody User user) throws BadRequestException {
            if (Validator.stringMaxLength(user.getFullName(), 100)) {
                        User userTemp = repository.findById(id).orElse(null);
                        userTemp.setFullName(user.getFullName());
                        userTemp.setEmail(user.getEmail());
                        userTemp.setUsername(user.getUsername());
                repo.saveAndFlush(userTemp);
        return "Success";
                    }
                    throw new BadRequestException(badRequestBinaryLength.replace("{tekst}", "slike"));


    }
}
