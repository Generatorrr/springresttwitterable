package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {UserMapper.class})
public interface ProjectMapper {

    @Mapping(target = "moduleCount", ignore = true)
    ListProjectDTO fromEntityToListProjectDTO(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "projectOwner", ignore = true)
    @Mapping(target = "modules", ignore = true)
    Project fromNewProjectDTOToEntity(NewProjectDTO newProjectDTO);

    List<ListProjectDTO> convertToList(Page<Project> projects);
}
