package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.requirement.FullRequirementDTO;
import com.example.springresttwitterable.entity.dto.testcase.FullTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.NewTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.UpdateTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.service.TestCaseService;
import com.example.springresttwitterable.service.TestPlanService;
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
@RequestMapping("/test-case")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListTestCaseDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return testCaseService.getById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    public FullTestCaseDTO getFullById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return testCaseService.getByFullDTOById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewTestCaseDTO dto) {

        TestCase newEntity = testCaseService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateTestCaseDTO dto) {

        testCaseService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        testCaseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignTo(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        testCaseService.assignTo(id, userId);
        return ResponseEntity.ok().build();
    }



    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFrom(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        testCaseService.detachFrom(id, userId);
        return ResponseEntity.ok().build();
    }
}
