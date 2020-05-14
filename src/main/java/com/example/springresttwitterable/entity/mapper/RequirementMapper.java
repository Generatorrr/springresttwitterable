package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.dto.requirement.ListRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.NewRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.UpdateRequirementDTO;
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
public interface RequirementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "testCases", ignore = true)
    Requirement fromNewRequirementDTOToEntity(NewRequirementDTO dto);

    @Mapping(target = "moduleId", source = "module.id")
    ListRequirementDTO fromEntityToListRequirementDTO(Requirement entity);

    Set<ListRequirementDTO> convertToSet(Set<Requirement> entities);

    default void updateEntityWithDTO(UpdateRequirementDTO dto, Requirement entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
    };
}
