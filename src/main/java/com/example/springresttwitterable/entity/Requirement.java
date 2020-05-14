package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "requirement")
@Data
public class Requirement extends AuditableEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestCase> testCases = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requirement)) return false;
        if (!super.equals(o)) return false;
        Requirement that = (Requirement) o;
        return getModule().equals(that.getModule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getModule());
    }

    @Override
    public String toString() {
        return "Requirement{" +
            "id=" + id +
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
