package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "test_plan")
@Audited
@Data
@EqualsAndHashCode(callSuper = true)
public class TestPlan extends AuditableEntity implements Serializable {

    @Column(name = "test_method")
    private String testMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;
}
