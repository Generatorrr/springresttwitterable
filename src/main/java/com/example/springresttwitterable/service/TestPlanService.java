package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.dto.module.FullModuleDTO;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.entity.mapper.ModuleMapper;
import com.example.springresttwitterable.entity.mapper.TestPlanMapper;
import com.example.springresttwitterable.repository.ModuleRepository;
import com.example.springresttwitterable.repository.ProjectRepository;
import com.example.springresttwitterable.repository.TestPlanRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class TestPlanService {

    private final ModuleRepository moduleRepository;
    private final TestPlanRepository testPlanRepository;
    private final TestPlanMapper testPlanMapper;

    public TestPlanService(ModuleRepository moduleRepository, TestPlanRepository testPlanRepository, TestPlanMapper testPlanMapper) {
        this.moduleRepository = moduleRepository;
        this.testPlanRepository = testPlanRepository;
        this.testPlanMapper = testPlanMapper;
    }

    public ListTestPlanDTO getById(Long id) {

        TestPlan entity = testPlanRepository.findById(id).orElseThrow();
        return testPlanMapper.fromEntityToListTestPlanDTO(entity);
    }

    public TestPlan create(NewTestPlanDTO dto) {

        Module parentEntity = moduleRepository.findById(dto.getModuleId()).orElseThrow();

        TestPlan newEntity = testPlanMapper.fromNewTestPlanDTOToEntity(dto);
        newEntity.setModule(parentEntity);
        testPlanRepository.save(newEntity);
        return newEntity;
    }

    public void update(UpdateTestPlanDTO dto) {

        TestPlan entity = testPlanRepository.findById(dto.getId()).orElseThrow();
        testPlanMapper.updateEntityWithDTO(dto, entity);
        testPlanRepository.save(entity);
    }

    public void delete(Long id) {
        TestPlan entity = testPlanRepository.findById(id).orElseThrow();
        testPlanRepository.delete(entity);
    }
}
