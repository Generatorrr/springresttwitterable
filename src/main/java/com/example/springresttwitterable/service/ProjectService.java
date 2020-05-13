package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.UpdateProjectDTO;
import com.example.springresttwitterable.entity.mapper.ProjectMapper;
import com.example.springresttwitterable.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public void updateProject(UpdateProjectDTO updateProjectDTO) {

        Project project = projectRepository.findById(updateProjectDTO.getId()).orElseThrow();
        projectMapper.updateEntityWithDTO(updateProjectDTO, project);
        projectRepository.save(project);
    }

    public ListProjectDTO getById(Long id) {

        Project project = projectRepository.findById(id).orElseThrow();
        return projectMapper.fromEntityToListProjectDTO(project);
    }
}
