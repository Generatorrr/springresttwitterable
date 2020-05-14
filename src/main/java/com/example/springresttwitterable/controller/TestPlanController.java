package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.service.TestPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Test Plan API", description = "Operations with test plans")
@RequestMapping("/test-plan")
public class TestPlanController {

    private final TestPlanService testPlanService;

    public TestPlanController(TestPlanService testPlanService) {
        this.testPlanService = testPlanService;
    }

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value = "Get by id", response = ListTestPlanDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return ListModuleDTO by id"),
        @ApiResponse(code = 403, message = "Forbidden")
    }
    )
    public ListTestPlanDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return testPlanService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new test plan", response = ResponseEntity.class)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewTestPlanDTO dto) {

        TestPlan newEntity = testPlanService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update test plan", response = ResponseEntity.class)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateTestPlanDTO dto) {

        testPlanService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete test plan by id", response = ResponseEntity.class)
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        testPlanService.delete(id);
        return ResponseEntity.ok().build();
    }
}
