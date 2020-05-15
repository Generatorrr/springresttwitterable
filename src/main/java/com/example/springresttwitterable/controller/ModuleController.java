package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.module.FullModuleDTO;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.mapper.ModuleMapper;
import com.example.springresttwitterable.service.ModuleService;
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
@RequestMapping("/module")
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleMapper moduleMapper;

    public ModuleController(ModuleService moduleService, ModuleMapper moduleMapper) {
        this.moduleService = moduleService;
        this.moduleMapper = moduleMapper;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListModuleDTO getModule(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return moduleService.getListModuleDTOById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    public FullModuleDTO getFullById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return moduleService.getByFullModuleDTOById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createModule(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewModuleDTO newModuleDTO) {

        Module newModule = moduleService.createModule(newModuleDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProject(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateModuleDTO updateModuleDTO) {

        moduleService.updateModule(updateModuleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity assignToProject(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        moduleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignTo(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        moduleService.assignTo(id, userId);
        return ResponseEntity.ok().build();
    }



    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFrom(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        moduleService.detachFrom(id, userId);
        return ResponseEntity.ok().build();
    }
}
