package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.testcase.FullTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.NewTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.entity.mapper.TestCaseMapper;
import com.example.springresttwitterable.repository.RequirementRepository;
import com.example.springresttwitterable.repository.TestCaseRepository;
import com.example.springresttwitterable.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class TestCaseService {

    private final RequirementRepository requirementRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMapper testCaseMapper;
    private final UserRepository userRepository;

    public TestCaseService(RequirementRepository requirementRepository, TestCaseRepository testCaseRepository,
                           TestCaseMapper testCaseMapper, UserRepository userRepository) {
        this.requirementRepository = requirementRepository;
        this.testCaseRepository = testCaseRepository;
        this.testCaseMapper = testCaseMapper;
        this.userRepository = userRepository;
    }

    public ListTestCaseDTO getById(Long id) {

        TestCase entity = testCaseRepository.findById(id).orElseThrow();
        return testCaseMapper.fromEntityToListTestCaseDTO(entity);
    }

    public FullTestCaseDTO getByFullDTOById(Long id) {

        TestCase module = testCaseRepository.findById(id).orElseThrow();
        return testCaseMapper.fromEntityToFullDTO(module);
    }

    public TestCase create(NewTestCaseDTO dto) {

        Requirement parentEntity = requirementRepository.findById(dto.getRequirementId()).orElseThrow();

        TestCase newEntity = testCaseMapper.fromNewDTOToEntity(dto);
        newEntity.setRequirement(parentEntity);
        testCaseRepository.save(newEntity);
        return newEntity;
    }

    public void update(UpdateTestCaseDTO dto) {

        TestCase entity = testCaseRepository.findById(dto.getId()).orElseThrow();
        testCaseMapper.updateEntityWithDTO(dto, entity);
        testCaseRepository.save(entity);
    }

    public void delete(Long id) {
        TestCase entity = testCaseRepository.findById(id).orElseThrow();
        testCaseRepository.delete(entity);
    }

    public void assignTo(Long id, String userId) {

        TestCase entity = testCaseRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().add(user);
        testCaseRepository.save(entity);
    }

    public void detachFrom(Long id, String userId) {

        TestCase entity = testCaseRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (!entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().remove(user);
        testCaseRepository.save(entity);
    }
}
