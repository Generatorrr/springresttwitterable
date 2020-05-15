package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.Task;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.task.ListTaskDTO;
import com.example.springresttwitterable.entity.dto.task.NewTaskDTO;
import com.example.springresttwitterable.entity.dto.task.UpdateTaskDTO;
import com.example.springresttwitterable.entity.dto.testcase.FullTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.NewTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import com.example.springresttwitterable.entity.mapper.TaskMapper;
import com.example.springresttwitterable.repository.TaskRepository;
import com.example.springresttwitterable.repository.TestCaseRepository;
import com.example.springresttwitterable.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class TaskService {

    private final TestCaseRepository testCaseRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskService(TestCaseRepository testCaseRepository, TaskRepository taskRepository, TaskMapper taskMapper,
                       UserRepository userRepository) {
        this.testCaseRepository = testCaseRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public ListTaskDTO getById(Long id) {

        Task entity = taskRepository.findById(id).orElseThrow();
        return taskMapper.fromEntityToListDTO(entity);
    }

    public Task create(NewTaskDTO dto) {

        TestCase parentEntity = testCaseRepository.findById(dto.getTestCaseId()).orElseThrow();

        Task newEntity = taskMapper.fromNewDTOToEntity(dto);
        newEntity.setTestCase(parentEntity);
        taskRepository.save(newEntity);
        return newEntity;
    }

    public void update(UpdateTaskDTO dto) {

        Task entity = taskRepository.findById(dto.getId()).orElseThrow();
        taskMapper.updateEntityWithDTO(dto, entity);
        taskRepository.save(entity);
    }

    public void delete(Long id) {
        Task entity = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(entity);
    }

    public void assignTo(Long id, String userId) {

        Task entity = taskRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().add(user);
        taskRepository.save(entity);
    }

    public void detachFrom(Long id, String userId) {

        Task entity = taskRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (!entity.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        entity.getUsers().remove(user);
        taskRepository.save(entity);
    }
}
