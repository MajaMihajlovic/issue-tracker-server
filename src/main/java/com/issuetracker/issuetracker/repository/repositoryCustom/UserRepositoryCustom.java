package com.issuetracker.issuetracker.repository.repositoryCustom;

import com.issuetracker.issuetracker.model.User;

public interface UserRepositoryCustom {

        User login(String username, String password);
}
