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
@Table(name = "project")
@Audited
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends AuditableEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_owner")
    private User projectOwner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Module> modules = new HashSet<>();
}
