package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Task;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.task.ListTaskDTO;
import com.example.springresttwitterable.entity.dto.task.NewTaskDTO;
import com.example.springresttwitterable.entity.dto.task.UpdateTaskDTO;
import com.example.springresttwitterable.entity.dto.testcase.FullTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.NewTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import com.example.springresttwitterable.service.TaskService;
import com.example.springresttwitterable.service.TestCaseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListTaskDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return taskService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewTaskDTO dto) {

        Task newEntity = taskService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateTaskDTO dto) {

        taskService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignTo(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        taskService.assignTo(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFrom(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        taskService.detachFrom(id, userId);
        return ResponseEntity.ok().build();
    }
}
