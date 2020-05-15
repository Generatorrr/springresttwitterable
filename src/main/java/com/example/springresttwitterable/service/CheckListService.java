package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.CheckList;
import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.TestCaseCheckList;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.checklist.FullCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.checklist.NewCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.enums.Status;
import com.example.springresttwitterable.entity.mapper.CheckListMapper;
import com.example.springresttwitterable.entity.mapper.TestCaseCheckListMapper;
import com.example.springresttwitterable.entity.mapper.TestCaseMapper;
import com.example.springresttwitterable.repository.CheckListRepository;
import com.example.springresttwitterable.repository.ModuleRepository;
import com.example.springresttwitterable.repository.TestCaseCheckListRepository;
import com.example.springresttwitterable.repository.TestCaseRepository;
import com.example.springresttwitterable.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class CheckListService {

    private final ModuleRepository moduleRepository;
    private final CheckListRepository checkListRepository;
    private final TestCaseCheckListRepository testCaseCheckListRepository;
    private final TestCaseRepository testCaseRepository;
    private final CheckListMapper checkListMapper;
    private final TestCaseCheckListMapper testCaseCheckListMapper;
    private final TestCaseMapper testCaseMapper;
    private final UserRepository userRepository;

    public CheckListService(ModuleRepository moduleRepository, CheckListRepository checkListRepository,
                            TestCaseCheckListRepository testCaseCheckListRepository, TestCaseRepository testCaseRepository,
                            CheckListMapper checkListMapper, TestCaseCheckListMapper testCaseCheckListMapper,
                            TestCaseMapper testCaseMapper, UserRepository userRepository) {
        this.moduleRepository = moduleRepository;
        this.checkListRepository = checkListRepository;
        this.testCaseCheckListRepository = testCaseCheckListRepository;
        this.testCaseRepository = testCaseRepository;
        this.checkListMapper = checkListMapper;
        this.testCaseCheckListMapper = testCaseCheckListMapper;
        this.testCaseMapper = testCaseMapper;
        this.userRepository = userRepository;
    }

    public ListCheckListDTO getById(Long id) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        return checkListMapper.fromEntityToListTestCaseDTO(entity);
    }

    public FullCheckListDTO getByFullDTOById(Long id) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        FullCheckListDTO dto = checkListMapper.fromEntityToFullDTO(entity);
        dto.setCheckListTestCases(testCaseCheckListMapper.fromEntitiesToDTOs(entity.getTestCases()));

        return dto;
    }

    public CheckList create(NewCheckListDTO dto) {

        Module parentEntity = moduleRepository.findById(dto.getModuleId()).orElseThrow();

        CheckList newEntity = checkListMapper.fromNewDTOToEntity(dto);
        newEntity.setModule(parentEntity);
        checkListRepository.save(newEntity);
        return newEntity;
    }

    public void update(UpdateCheckListDTO dto) {

        CheckList entity = checkListRepository.findById(dto.getId()).orElseThrow();
        checkListMapper.updateEntityWithDTO(dto, entity);
        checkListRepository.save(entity);
    }

    public void delete(Long id) {
        CheckList entity = checkListRepository.findById(id).orElseThrow();
        checkListRepository.delete(entity);
    }

    public void assignTo(Long id, String userId) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().add(user);
        checkListRepository.save(entity);
    }

    public void detachFrom(Long id, String userId) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (!entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().remove(user);
        checkListRepository.save(entity);
    }

    public Set<ListTestCaseDTO> getAvailableTestCases(Long id) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        Set<TestCase> availableTestCases = testCaseRepository.findByModuleId(entity.getModule().getId());
        return testCaseMapper.convertToSet(availableTestCases);
    }

    public Integer assignTestCase(Long id, Long testCaseId) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow();

        TestCaseCheckList alreadyAvailableEntity = testCaseCheckListRepository.findByTestCaseAndAndCheckList(testCase, entity);

        if (null != alreadyAvailableEntity) {
            throw new RuntimeException();
        }

        TestCaseCheckList newAssigningEntity = new TestCaseCheckList();
        newAssigningEntity.setName("Name");
        newAssigningEntity.setDescription("Description");
        newAssigningEntity.setCheckList(entity);
        newAssigningEntity.setStatus(Status.TO_DO);
        newAssigningEntity.setTestCase(testCase);
        newAssigningEntity.setTestCaseOrder(entity.getTestCases().size() + 1);
        testCaseCheckListRepository.save(newAssigningEntity);
        return newAssigningEntity.getTestCaseOrder();
    }

    public void detachTestCase(Long id, Long testCaseId) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow();

        TestCaseCheckList assigningEntity = testCaseCheckListRepository.findByTestCaseAndAndCheckList(testCase, entity);

        if (null == assigningEntity) {
            throw new RuntimeException();
        }
        testCaseCheckListRepository.delete(assigningEntity);

        List<TestCaseCheckList> recreatedRangeList = entity.getTestCases().stream()
            .sorted(Comparator.comparingInt(TestCaseCheckList::getTestCaseOrder))
            .collect(Collectors.toList());

        recreatedRangeList.forEach(item -> {
            int newOrderValue = recreatedRangeList.indexOf(item) + 1;
            item.setTestCaseOrder(newOrderValue);
            testCaseCheckListRepository.save(item);
        });
    }

    public ListCheckListTestCaseDTO getTestCaseCheckListForEdit(Long id, Long testCaseId) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow();
        TestCaseCheckList assigningEntity = testCaseCheckListRepository.findByTestCaseAndAndCheckList(testCase, entity);

        if (null == assigningEntity) {
            throw new RuntimeException();
        }

        return testCaseCheckListMapper.fromEntityToListTestCaseDTO(assigningEntity);
    }

    public void updateCheckListTestCase(Long id, Long testCaseId, UpdateCheckListTestCaseDTO dto) {

        CheckList entity = checkListRepository.findById(id).orElseThrow();
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow();

        TestCaseCheckList assigningEntity = testCaseCheckListRepository.findByTestCaseAndAndCheckList(testCase, entity);

        if (null == assigningEntity) {
            throw new RuntimeException();
        }

        int orderFromUi = dto.getTestCaseOrder();
        int orderFromDB = assigningEntity.getTestCaseOrder();

        entity.getTestCases().forEach(item -> {
            if (item.getTestCaseOrder() == orderFromUi) {
                item.setTestCaseOrder(orderFromDB);
                testCaseCheckListRepository.save(item);
            }
        });

        testCaseCheckListMapper.updateEntityWithDTO(dto, assigningEntity);
        testCaseCheckListRepository.save(assigningEntity);
    }
}
