package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.TestCaseCheckList;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {TestCaseMapper.class})
public interface TestCaseCheckListMapper {


    ListCheckListTestCaseDTO fromEntityToListTestCaseDTO(TestCaseCheckList entity);

    Set<ListCheckListTestCaseDTO> fromEntitiesToDTOs(Set<TestCaseCheckList> entities);

    default void updateEntityWithDTO(UpdateCheckListTestCaseDTO dto, TestCaseCheckList entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setTestCaseOrder(dto.getTestCaseOrder());
    }
}
