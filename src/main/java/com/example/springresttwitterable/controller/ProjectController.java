package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.PageDTO;
import com.example.springresttwitterable.entity.dto.project.NewProjectDTO;
import com.example.springresttwitterable.entity.dto.project.ProjectDTO;
import com.example.springresttwitterable.entity.mapper.ProjectMapper;
import com.example.springresttwitterable.repository.ProjectRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(value="Project API", description="Operations with projects")
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "Get messages", response = ProjectDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return projects"),
        @ApiResponse(code = 403, message = "Forbidden")
    }
    )
    public ProjectDTO getMessages(
        @RequestParam(required = false, defaultValue = "") String filter,
        @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        final Page<Project> toResponse;

        if (null != filter && !filter.isEmpty()) {
            toResponse = projectRepository.findByNameContaining(filter, pageable);
        } else {
            toResponse = projectRepository.findAll(pageable);
        }

        Pageable pageInfo = toResponse.getPageable();
        return new ProjectDTO(
            projectMapper.convertToList(toResponse),
            new PageDTO(toResponse.getTotalPages(), (int) toResponse.getTotalElements(), pageInfo.getPageNumber(), pageInfo.getPageSize())
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new project", response = ResponseEntity.class)
    public ResponseEntity createProject(@AuthenticationPrincipal User currentUser, @Valid NewProjectDTO newProjectDTO )
    {

        Project project = projectMapper.fromNewProjectDTOToEntity(newProjectDTO);
        project.setProjectOwner(currentUser);
        projectRepository.save(project);

        return ResponseEntity.ok().build();
    }
}
