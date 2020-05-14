package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 2020-05-11
 *
 * @author generatorr
 */

@Entity
@Table(name = "project")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Project extends AuditableEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_owner")
    private User projectOwner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Module> modules = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "account_project",
        joinColumns = { @JoinColumn(name = "project_id") },
        inverseJoinColumns = { @JoinColumn(name = "account_id") }
    )
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return getProjectOwner().equals(project.getProjectOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProjectOwner());
    }

    @Override
    public String toString() {
        return "Project{" +
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
