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

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Entity
@Table(name = "task")
@Audited
@Data
@EqualsAndHashCode(callSuper = true)
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
}
