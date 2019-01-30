package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Integer> {
}
