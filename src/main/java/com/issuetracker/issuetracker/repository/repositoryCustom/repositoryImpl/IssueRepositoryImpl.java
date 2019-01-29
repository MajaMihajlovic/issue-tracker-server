package com.issuetracker.issuetracker.repository.repositoryCustom.repositoryImpl;

import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.repository.repositoryCustom.IssueRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IssueRepositoryImpl implements IssueRepositoryCustom{

    private static final String QUERY="select i.id, title, i.description, s.name as state, p.name as priority, duedate,\n" +
            " created_date, t.name as type, r.full_name, a.full_name as assignee_full_name, assignee_id,\n" +
            " v.name as version, p.name as project_name, i.project_id from issue as i \n" +
            " left join state as s on s.id=i.state_id left join  priority as p  on p.id=priority_id\n" +
            " left join  type as t on t.id=type_id left join user as r on r.id=reporter_id \n" +
            " left join user as a on a.id=assignee_id\n" +
            " left join version as v on v.id=version_id;";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public List<IssueCustom> getAllIssues() {
        List<IssueCustom> list = entityManager.createNativeQuery(QUERY,"IssueMapping").getResultList();
        if(list == null || list.isEmpty()){
            return null;
        }
        else{
            return list;
        }
    }
    }
