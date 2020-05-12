package com.example.springresttwitterable.entity.base;

import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.enums.Status;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created on 2020-05-11
 *
 * @author generatorr
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Column(name = "init_date")
    protected LocalDateTime initialDate;

    @Column(name = "end_date")
    protected LocalDateTime endDate;

    @CreationTimestamp
    @Column(name = "created_on")
    protected LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    protected LocalDateTime updatedOn;

    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    protected User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by")
    protected User updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected Status status;
}
