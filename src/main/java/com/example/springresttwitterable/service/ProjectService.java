package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.project.FullProjectDTO;
import com.example.springresttwitterable.entity.dto.project.ListProjectDTO;
import com.example.springresttwitterable.entity.dto.project.UpdateProjectDTO;
import com.example.springresttwitterable.entity.mapper.ProjectMapper;
import com.example.springresttwitterable.repository.ProjectRepository;
import com.example.springresttwitterable.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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

    public FullProjectDTO getByFullProjectDTOById(Long id) {

        Project project = projectRepository.findById(id).orElseThrow();
        return projectMapper.fromEntityToFullProjectDTO(project);
    }

    public void assignToProject(Long id, String userId) {

        Project project = projectRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (project.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        project.getUsers().add(user);
        projectRepository.save(project);
    }

    public void detachFromProject(Long id, String userId) {

        Project project = projectRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (!project.getUsers().contains(user)) {
            throw new RuntimeException();
        }

        project.getUsers().remove(user);
        projectRepository.save(project);
    }
}
