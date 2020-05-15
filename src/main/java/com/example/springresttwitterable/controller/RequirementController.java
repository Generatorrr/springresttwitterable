package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Requirement;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.requirement.FullRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.ListRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.NewRequirementDTO;
import com.example.springresttwitterable.entity.dto.requirement.UpdateRequirementDTO;
import com.example.springresttwitterable.service.RequirementService;
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
@RequestMapping("/requirement")
public class RequirementController {

    private final RequirementService requirementService;

    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListRequirementDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return requirementService.getById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    public FullRequirementDTO getFullById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return requirementService.getByFullModuleDTOById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewRequirementDTO dto) {

        Requirement newEntity = requirementService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateRequirementDTO dto) {

        requirementService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        requirementService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignTo(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        requirementService.assignTo(id, userId);
        return ResponseEntity.ok().build();
    }



    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFrom(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        requirementService.detachFrom(id, userId);
        return ResponseEntity.ok().build();
    }
}
