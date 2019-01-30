package com.issuetracker.issuetracker.repository.repositoryCustom;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.model.modelCustom.UserCustom;

import java.util.List;

public interface UserRepositoryCustom {

        User login(String username, String password);

        List<UserCustom> getAllFiltered();

        List<UserCustom> getParticipants(Integer id);
}
