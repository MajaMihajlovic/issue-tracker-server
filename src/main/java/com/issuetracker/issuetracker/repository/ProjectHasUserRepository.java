package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Priority;
import com.issuetracker.issuetracker.model.ProjectHasUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectHasUserRepository extends JpaRepository<ProjectHasUser, Integer> {
}
