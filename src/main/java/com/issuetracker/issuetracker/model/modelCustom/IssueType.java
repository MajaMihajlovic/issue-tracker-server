package com.issuetracker.issuetracker.model.modelCustom;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
@SuppressWarnings("WeakerAccess")
@SqlResultSetMapping(
        name = "IssueType",
        classes = @ConstructorResult(
                targetClass = IssueType.class,
                columns = {
                        @ColumnResult(name="type"),
                        @ColumnResult(name="count", type=Integer.class),
                }
        )
)
@MappedSuperclass
public class IssueType {

    private String type;
    private Integer count;

    public IssueType(String type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public IssueType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
