package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.Task;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.dto.task.ListTaskDTO;
import com.example.springresttwitterable.entity.dto.task.NewTaskDTO;
import com.example.springresttwitterable.entity.dto.task.UpdateTaskDTO;
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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {TestCase.class})
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "testCase", ignore = true)
    @Mapping(target = "users", ignore = true)
    Task fromNewDTOToEntity(NewTaskDTO dto);

    @Mapping(target = "testCaseId", source = "testCase.id")
    ListTaskDTO fromEntityToListDTO(Task entity);

    Set<ListTaskDTO> convertToSet(Set<Task> entities);

    default void updateEntityWithDTO(UpdateTaskDTO dto, Task entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        entity.setTaskType(dto.getTaskType());
        entity.setSeverity(dto.getSeverity());

    };
}
