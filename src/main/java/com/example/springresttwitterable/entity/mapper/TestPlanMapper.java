package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ModuleMapper.class})
public interface TestPlanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "users", ignore = true)
    TestPlan fromNewTestPlanDTOToEntity(NewTestPlanDTO dto);

    @Mapping(target = "moduleId", source = "module.id")
    ListTestPlanDTO fromEntityToListTestPlanDTO(TestPlan entity);

    Set<ListTestPlanDTO> convertToSet(Set<TestPlan> entities);

    default void updateEntityWithDTO(UpdateTestPlanDTO dto, TestPlan entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setTestMethod(dto.getTestMethod());
        entity.setStatus(dto.getStatus());
    };
}
