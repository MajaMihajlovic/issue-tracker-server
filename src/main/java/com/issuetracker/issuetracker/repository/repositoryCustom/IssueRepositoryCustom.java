package com.issuetracker.issuetracker.repository.repositoryCustom;

import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;

import java.util.List;

public interface IssueRepositoryCustom {

    List<IssueCustom> getAllIssues();
    List<IssueType> countIssueType(Integer projectId);
}
