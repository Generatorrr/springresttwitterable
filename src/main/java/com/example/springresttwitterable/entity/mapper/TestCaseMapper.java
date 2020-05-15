package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.dto.testcase.FullTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.NewTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {RequirementMapper.class, TaskMapper.class,
    UserMapper.class})
public interface TestCaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "requirement", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "checkLists", ignore = true)
    @Mapping(target = "users", ignore = true)
    TestCase fromNewDTOToEntity(NewTestCaseDTO dto);

    @Mapping(target = "requirementId", source = "requirement.id")
    ListTestCaseDTO fromEntityToListTestCaseDTO(TestCase entity);

    @Mapping(target = "requirementId", source = "requirement.id")
    FullTestCaseDTO fromEntityToFullDTO(TestCase module);

    Set<ListTestCaseDTO> convertToSet(Set<TestCase> entities);

    default void updateEntityWithDTO(UpdateTestCaseDTO dto, TestCase entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setTestCase(dto.getTestCase());
        entity.setStatus(dto.getStatus());
    }
}
