package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Entity
@Table(name = "test_plan")
@Data
public class TestPlan extends AuditableEntity implements Serializable {

    @Column(name = "test_method")
    private String testMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "account_test_plan",
        joinColumns = { @JoinColumn(name = "test_plan_id") },
        inverseJoinColumns = { @JoinColumn(name = "account_id") }
    )
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestPlan)) return false;
        if (!super.equals(o)) return false;
        TestPlan testPlan = (TestPlan) o;
        return getTestMethod().equals(testPlan.getTestMethod()) &&
            getModule().equals(testPlan.getModule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTestMethod(), getModule());
    }

    @Override
    public String toString() {
        return "TestPlan{" +
            "testMethod='" + testMethod + '\'' +
            ", id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", initialDate=" + initialDate +
            ", endDate=" + endDate +
            ", createdOn=" + createdOn +
            ", updatedOn=" + updatedOn +
            ", status=" + status +
            '}';
    }
}
