package com.example.springresttwitterable.entity.dto.checklist;

import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created on 2020-05-15
 *
 * @author generatorr
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCheckListTestCaseDTO {

    private String name;
    private String description;
    private Status status;
    private ListTestCaseDTO testCase;
    private Integer testCaseOrder;
}
