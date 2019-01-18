package com.issuetracker.issuetracker.repository.repositoryCustom.repositoryImpl;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.repositoryCustom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private static final String SQL_LOGIN = "SELECT DISTINCT u.id, u.email, u.username, u.password, u.full_name, u.photo, u.active, u.deleted, u.token, u.token_time,u.is_admin FROM user u  WHERE u.username=? AND u.password=SHA2(?, 512) ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User login(String username,String password) {
        List<User> user = entityManager.createNativeQuery(SQL_LOGIN, "UserMapping").setParameter(1, username).setParameter(2, password).getResultList();
        if(user == null || user.isEmpty()){
            return null;
        }
        else{
            return user.get(0);
        }
    }
}
