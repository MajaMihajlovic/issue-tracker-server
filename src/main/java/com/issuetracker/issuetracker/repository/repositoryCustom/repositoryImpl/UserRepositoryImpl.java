package com.issuetracker.issuetracker.repository.repositoryCustom.repositoryImpl;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.model.modelCustom.UserCustom;
import com.issuetracker.issuetracker.repository.repositoryCustom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.StyleSheet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final String SQL_LOGIN = "SELECT DISTINCT u.id, u.email, u.username, u.password, u.full_name, u.photo, u.active, u.deleted, u.token, u.token_time,u.is_admin FROM user u  WHERE u.username=? AND u.password=SHA2(?, 512) ";
    private static final String SQL_FILTER = "SELECT DISTINCT u.id, u.email, u.full_name from user u where u.active=1";
    private static final String SQL_SELECT_PARTICIPANTS="SELECT DISTINCT u.id, u.email, u.full_name from user u  join project_has_user as p on p.project_id=? where u.id=p.user_id and u.active=1";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User login(String username, String password) {
        List<User> user = entityManager.createNativeQuery(SQL_LOGIN, "UserMapping").setParameter(1, username).setParameter(2, password).getResultList();
        if (user == null || user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public List<UserCustom> getAllFiltered() {
        List<UserCustom> user = entityManager.createNativeQuery(SQL_FILTER, "UserCustom").getResultList();
        if (user == null || user.isEmpty()) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public List<UserCustom> getParticipants(Integer id) {
        List<UserCustom> user = entityManager.createNativeQuery(SQL_SELECT_PARTICIPANTS, "UserCustom").setParameter(1,id).getResultList();
        if (user == null || user.isEmpty()) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public List<UserCustom> getNonParticipants(Integer id) {
        List<UserCustom> list=getAllFiltered();
        List<UserCustom> copy=new ArrayList<>();
        List<UserCustom> participants=getParticipants(id);
        if(participants!=null){
        List<Integer> badIds=participants.stream().map(UserCustom::getId).collect(Collectors.toList());
        for(UserCustom user: list){
            if(!badIds.contains(user.getId())){
                copy.add(user);
            }
        }}else return list;
        return copy;
    }

}
