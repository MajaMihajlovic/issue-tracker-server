package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.repository.repositoryCustom.IssueRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Integer> , IssueRepositoryCustom {
}
