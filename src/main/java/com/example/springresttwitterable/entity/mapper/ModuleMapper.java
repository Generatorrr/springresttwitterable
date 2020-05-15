package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.dto.module.FullModuleDTO;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {ProjectMapper.class, TestPlanMapper.class, RequirementMapper.class, CheckListMapper.class, UserMapper.class})
public interface ModuleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "testPlans", ignore = true)
    @Mapping(target = "requirements", ignore = true)
    @Mapping(target = "checkLists", ignore = true)
    @Mapping(target = "users", ignore = true)
    Module fromNewModuleDTOToEntity(NewModuleDTO newModuleDTO);

    @Mapping(target = "projectId", source = "project.id")
    ListModuleDTO fromEntityToListModuleDTO(Module entity);

    @Mapping(target = "projectId", source = "project.id")
    FullModuleDTO fromEntityToFullModuleDTO(Module module);

    Set<ListModuleDTO> convertToSet(Set<Module> entities);

    default void updateEntityWithDTO(UpdateModuleDTO dto, Module entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
    }
}
