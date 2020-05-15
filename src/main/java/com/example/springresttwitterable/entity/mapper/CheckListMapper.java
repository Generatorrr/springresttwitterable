package com.example.springresttwitterable.entity.mapper;

import com.example.springresttwitterable.entity.CheckList;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.dto.checklist.FullCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.NewCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListDTO;
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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {ModuleMapper.class, UserMapper.class})
public interface CheckListMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "testCases", ignore = true)
    @Mapping(target = "users", ignore = true)
    CheckList fromNewDTOToEntity(NewCheckListDTO dto);

    @Mapping(target = "moduleId", source = "module.id")
    ListCheckListDTO fromEntityToListTestCaseDTO(CheckList entity);

    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "checkListTestCases", ignore = true)
    FullCheckListDTO fromEntityToFullDTO(CheckList entity);

    Set<ListCheckListDTO> convertToSet(Set<CheckList> entities);

    default void updateEntityWithDTO(UpdateCheckListDTO dto, CheckList entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(dto.getInitialDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
    }

    ;
}
