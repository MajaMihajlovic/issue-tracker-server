package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer>, UserRepositoryCustom {

    User getByUsername(String username);
    User getByToken(String token);
    Integer countAllByEmail(String email);
    List<User> getAllByActive(byte active);
}