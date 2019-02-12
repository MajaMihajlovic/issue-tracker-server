package com.issuetracker.issuetracker.repository.repositoryCustom.repositoryImpl;

import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;
import com.issuetracker.issuetracker.repository.repositoryCustom.IssueRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IssueRepositoryImpl implements IssueRepositoryCustom{

    private static final String QUERY="select i.id, title, i.description, s.name as state, p.name as priority, duedate,\n" +
            " created_date, t.name as type, r.full_name, a.full_name as assignee_full_name, assignee_id,\n" +
            " v.name as version, pr.name as project_name, i.project_id from issue as i \n" +
            " left join state as s on s.id=i.state_id left join project as pr on pr.id=i.project_id left join  priority as p  on p.id=priority_id\n" +
            " left join  type as t on t.id=type_id left join user as r on r.id=reporter_id \n" +
            " left join user as a on a.id=assignee_id\n" +
            " left join version as v on v.id=version_id;";

    private static final String COUNT_ISSUES_BY_TYPE="select name as type,count(name) as count from issue i inner join type t on t.id=i.type_id  where project_id=?  group by name;";
    private static final String COUNT_ISSUES_BY_PRIORITY="select name as type,count(name) as count from issue i inner join priority t on t.id=i.priority_id  where project_id=?  group by name;";
    private static final String COUNT_ISSUES_BY_STATE="select name as type,count(name) as count from issue i inner join state t on t.id=i.state_id  where project_id=?  group by name;";

    private static final String COUNT_ISSUES_PER_ASSIGNEE="select full_name as type,count(full_name) as count from issue i inner join user u on u.id=i.assignee_id  where project_id=?  group by full_name;";

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

    @Override
    public IssueCustom getCustomById(Integer id) {
        List<IssueCustom> list = entityManager.createNativeQuery(QUERY,"IssueMapping").getResultList();
        return list.stream().filter(e->e.getId()==id).findFirst().orElse(null);
    }

    @Override
    public List<IssueType> countIssueType(Integer projectId) {
        return entityManager.createNativeQuery(COUNT_ISSUES_BY_TYPE,"IssueType").setParameter(1,projectId).getResultList();
    }
    @Override
    public List<IssueType> countIssuePriority(Integer projectId) {
       return  entityManager.createNativeQuery(COUNT_ISSUES_BY_PRIORITY,"IssueType").setParameter(1,projectId).getResultList();
    }
    @Override
    public List<IssueType> countIssueState(Integer projectId) {
       return  entityManager.createNativeQuery(COUNT_ISSUES_BY_STATE,"IssueType").setParameter(1,projectId).getResultList();
    }

    @Override
    public List<IssueType> countIssuePerAssignee(Integer projectId) {
        return  entityManager.createNativeQuery(COUNT_ISSUES_PER_ASSIGNEE,"IssueType").setParameter(1,projectId).getResultList();
    }
    }

