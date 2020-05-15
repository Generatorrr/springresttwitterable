package com.example.springresttwitterable.entity;

import com.example.springresttwitterable.entity.base.TestCaseCheckListId;
import com.example.springresttwitterable.entity.enums.Status;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Entity
@Table(name = "test_case_check_list")
@Data
public class TestCaseCheckList implements Serializable {

    @EmbeddedId
    private TestCaseCheckListId id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("test_case_id")
    private TestCase testCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("check_list_id")
    private CheckList checkList;

    @Column(name = "test_case_order")
    private Integer testCaseOrder;

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

    @PrePersist
    private void prePersist() {
        if (null == getId()) {
            TestCaseCheckListId pk = new TestCaseCheckListId();
            pk.setCheckListId(getCheckList().getId());
            pk.setTestCaseId(getTestCase().getId());
            setId(pk);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCaseCheckList)) return false;
        TestCaseCheckList that = (TestCaseCheckList) o;
        return getId().equals(that.getId()) &&
            getTestCaseOrder().equals(that.getTestCaseOrder()) &&
            getCreatedOn().equals(that.getCreatedOn()) &&
            getUpdatedOn().equals(that.getUpdatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTestCaseOrder(), getCreatedOn(), getUpdatedOn());
    }

    @Override
    public String toString() {
        return "TestCaseCheckList{" +
            "id=" + id +
            ", testCase=" + testCase +
            ", checkList=" + checkList +
            ", testCaseOrder=" + testCaseOrder +
            ", createdOn=" + createdOn +
            ", updatedOn=" + updatedOn +
            '}';
    }
}
