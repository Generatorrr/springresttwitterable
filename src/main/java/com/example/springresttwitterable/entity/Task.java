package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import com.example.springresttwitterable.entity.enums.Severity;
import com.example.springresttwitterable.entity.enums.TaskType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Entity
@Table(name = "task")
@Data
public class Task extends AuditableEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type")
    protected TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    protected Severity severity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return getTaskType() == task.getTaskType() &&
            getSeverity() == task.getSeverity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaskType(), getSeverity());
    }

    @Override
    public String toString() {
        return "Task{" +
            "taskType=" + taskType +
            ", severity=" + severity +
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
