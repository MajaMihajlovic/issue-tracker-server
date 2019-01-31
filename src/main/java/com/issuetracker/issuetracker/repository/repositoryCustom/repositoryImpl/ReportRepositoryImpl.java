package com.issuetracker.issuetracker.repository.repositoryCustom.repositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReportRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;
}
