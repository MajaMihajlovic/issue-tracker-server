package com.issuetracker.issuetracker.repository.repositoryCustom;

import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;

import java.util.List;

public interface IssueRepositoryCustom {

    List<IssueCustom> getAllIssues();
}
