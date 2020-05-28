package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.dto.project.FullProjectDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
import com.example.springresttwitterable.entity.dto.project.UpdateProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {UserMapper.class, ModuleMapper.class})
public interface ProjectMapper {

    ListProjectDTO fromEntityToListProjectDTO(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "projectOwner", ignore = true)
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "users", ignore = true)
    Project fromNewProjectDTOToEntity(NewProjectDTO newProjectDTO);

    List<ListProjectDTO> convertToList(Page<Project> projects);

    FullProjectDTO fromEntityToFullProjectDTO(Project project);

    default void updateEntityWithDTO(UpdateProjectDTO dto, Project entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
    }
}
