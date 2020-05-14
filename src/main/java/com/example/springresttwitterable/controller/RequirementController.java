package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.TestPlan;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.requirement.ListRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.NewRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.UpdateRequirementDTO;
import com.example.springresttwitterable.entity.dto.testplan.ListTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.NewTestPlanDTO;
import com.example.springresttwitterable.entity.dto.testplan.UpdateTestPlanDTO;
import com.example.springresttwitterable.service.RequirementService;
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
@Api(value = "Requirement API", description = "Operations with requirements")
@RequestMapping("/requirement")
public class RequirementController {

    private final RequirementService requirementService;

    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value = "Get by id", response = ListRequirementDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return ListModuleDTO by id"),
        @ApiResponse(code = 403, message = "Forbidden")
    }
    )
    public ListRequirementDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return requirementService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new requirement", response = ResponseEntity.class)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewRequirementDTO dto) {

        Requirement newEntity = requirementService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update requirement", response = ResponseEntity.class)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateRequirementDTO dto) {

        requirementService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete requirement by id", response = ResponseEntity.class)
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        requirementService.delete(id);
        return ResponseEntity.ok().build();
    }
}
