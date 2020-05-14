package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.dto.requirement.ListRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.NewRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.UpdateRequirementDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.entity.mapper.RequirementMapper;
import com.example.springresttwitterable.entity.mapper.TestPlanMapper;
import com.example.springresttwitterable.repository.ModuleRepository;
import com.example.springresttwitterable.repository.RequirementRepository;
import com.example.springresttwitterable.repository.TestPlanRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class RequirementService {

    private final ModuleRepository moduleRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementMapper requirementMapper;

    public RequirementService(ModuleRepository moduleRepository, RequirementRepository requirementRepository,
                              RequirementMapper requirementMapper) {
        this.moduleRepository = moduleRepository;
        this.requirementRepository = requirementRepository;
        this.requirementMapper = requirementMapper;
    }

    public ListRequirementDTO getById(Long id) {

        Requirement entity = requirementRepository.findById(id).orElseThrow();
        return requirementMapper.fromEntityToListRequirementDTO(entity);
    }

    public Requirement create(NewRequirementDTO dto) {

        Module parentEntity = moduleRepository.findById(dto.getModuleId()).orElseThrow();

        Requirement newEntity = requirementMapper.fromNewRequirementDTOToEntity(dto);
        newEntity.setModule(parentEntity);
        requirementRepository.save(newEntity);
        return newEntity;
    }

    public void update(UpdateRequirementDTO dto) {

        Requirement entity = requirementRepository.findById(dto.getId()).orElseThrow();
        requirementMapper.updateEntityWithDTO(dto, entity);
        requirementRepository.save(entity);
    }

    public void delete(Long id) {
        Requirement entity = requirementRepository.findById(id).orElseThrow();
        requirementRepository.delete(entity);
    }
}
