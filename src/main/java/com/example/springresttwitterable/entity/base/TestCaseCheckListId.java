package com.example.springresttwitterable.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TestCaseCheckListId implements Serializable {

    @Column(name = "test_case_id")
    @EqualsAndHashCode.Include
    private Long testCaseId;

    @Column(name = "check_list_id")
    @EqualsAndHashCode.Include
    private Long checkListId;
}
