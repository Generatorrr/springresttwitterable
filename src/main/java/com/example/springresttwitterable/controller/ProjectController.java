package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.PageDTO;
import com.example.springresttwitterable.entity.dto.project.FullProjectDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
import com.example.springresttwitterable.entity.dto.project.PageableProjectDTO;
import com.example.springresttwitterable.entity.dto.project.UpdateProjectDTO;
import com.example.springresttwitterable.entity.mapper.ProjectMapper;
import com.example.springresttwitterable.repository.ProjectRepository;
import com.example.springresttwitterable.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    @ResponseBody
    public PageableProjectDTO getProjects(
        @RequestParam(required = false, defaultValue = "") String filter,
        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        final Page<Project> toResponse;

        if (null != filter && !filter.isEmpty()) {
            toResponse = projectRepository.findByNameContaining(filter, pageable);
        } else {
            toResponse = projectRepository.findAll(pageable);
        }

        Pageable pageInfo = toResponse.getPageable();
        return new PageableProjectDTO(
            projectMapper.convertToList(toResponse),
            new PageDTO(toResponse.getTotalPages(), (int) toResponse.getTotalElements(), pageInfo.getPageNumber(), pageInfo.getPageSize())
        );
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListProjectDTO getProject(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return projectService.getById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    public FullProjectDTO getFullProject(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return projectService.getByFullProjectDTOById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProject(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewProjectDTO newProjectDTO) {

        Project project = projectMapper.fromNewProjectDTOToEntity(newProjectDTO);
        project.setProjectOwner(currentUser);
        projectRepository.save(project);

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProject(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateProjectDTO updateProjectDTO) {

        projectService.updateProject(updateProjectDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignToProject(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        projectService.assignToProject(id, userId);
        return ResponseEntity.ok().build();
    }



    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFromProject(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        projectService.detachFromProject(id, userId);
        return ResponseEntity.ok().build();
    }
}
