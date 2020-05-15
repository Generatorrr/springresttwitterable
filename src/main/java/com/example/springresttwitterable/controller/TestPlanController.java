package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
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
@RequestMapping("/test-plan")
public class TestPlanController {

    private final TestPlanService testPlanService;

    public TestPlanController(TestPlanService testPlanService) {
        this.testPlanService = testPlanService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListTestPlanDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return testPlanService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewTestPlanDTO dto) {

        TestPlan newEntity = testPlanService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateTestPlanDTO dto) {

        testPlanService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        testPlanService.delete(id);
        return ResponseEntity.ok().build();
    }
}
