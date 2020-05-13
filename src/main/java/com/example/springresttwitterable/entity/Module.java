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
import java.util.Set;

/**
 * Created on 2020-05-11
 *
 * @author generatorr
 */

@Entity
@Table(name = "module")
@Data
@EqualsAndHashCode(callSuper = true)
public class Module extends AuditableEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestPlan> testPlans = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Requirement> requirements = new HashSet<>();
}