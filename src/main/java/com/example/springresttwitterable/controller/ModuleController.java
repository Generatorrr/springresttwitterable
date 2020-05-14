package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.PageDTO;
import com.example.springresttwitterable.entity.dto.module.FullModuleDTO;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.dto.project.FullProjectDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
import com.example.springresttwitterable.entity.dto.project.PageableProjectDTO;
import com.example.springresttwitterable.entity.dto.project.UpdateProjectDTO;
import com.example.springresttwitterable.entity.mapper.ModuleMapper;
import com.example.springresttwitterable.entity.mapper.ProjectMapper;
import com.example.springresttwitterable.repository.ModuleRepository;
import com.example.springresttwitterable.repository.ProjectRepository;
import com.example.springresttwitterable.service.ModuleService;
import com.example.springresttwitterable.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@RestController
@Api(value = "Module API", description = "Operations with modules")
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
    @ApiOperation(value = "Get ListModuleDTO by id", response = ListModuleDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return ListModuleDTO by id"),
        @ApiResponse(code = 403, message = "Forbidden")
    }
    )
    public ListModuleDTO getModule(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return moduleService.getListModuleDTOById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    @ApiOperation(value = "Get FullModuleDTO by id", response = FullModuleDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return project by id"),
        @ApiResponse(code = 403, message = "Forbidden")
    }
    )
    public FullModuleDTO getFullProject(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return moduleService.getByFullModuleDTOById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new module", response = ResponseEntity.class)
    public ResponseEntity createModule(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewModuleDTO newModuleDTO) {

        Module newModule = moduleService.createModule(newModuleDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update module", response = ResponseEntity.class)
    public ResponseEntity updateProject(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateModuleDTO updateModuleDTO) {

        moduleService.updateModule(updateModuleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete module by id", response = ResponseEntity.class)
    public ResponseEntity assignToProject(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        moduleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
