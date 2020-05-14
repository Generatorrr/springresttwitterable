package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "check_list")
@Data
public class CheckList extends AuditableEntity implements Serializable {

    @OneToMany(
        mappedBy = "checkList",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<TestCaseCheckList> checkLists = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckList)) return false;
        if (!super.equals(o)) return false;
        CheckList checkList = (CheckList) o;
        return getCheckLists().equals(checkList.getCheckLists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCheckLists());
    }

    @Override
    public String toString() {
        return "CheckList{" +
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
