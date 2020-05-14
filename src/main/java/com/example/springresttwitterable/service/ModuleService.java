package com.example.springresttwitterable.service;

import com.example.springresttwitterable.entity.Module;
import com.example.springresttwitterable.entity.Project;
import com.example.springresttwitterable.entity.dto.module.FullModuleDTO;
import com.example.springresttwitterable.entity.dto.module.ListModuleDTO;
import com.example.springresttwitterable.entity.dto.module.NewModuleDTO;
import com.example.springresttwitterable.entity.dto.module.UpdateModuleDTO;
import com.example.springresttwitterable.entity.mapper.ModuleMapper;
import com.example.springresttwitterable.repository.ModuleRepository;
import com.example.springresttwitterable.repository.ProjectRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ProjectRepository projectRepository;
    private final ModuleMapper moduleMapper;

    public ModuleService(ModuleRepository moduleRepository, ProjectRepository projectRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.projectRepository = projectRepository;
        this.moduleMapper = moduleMapper;
    }

    public ListModuleDTO getListModuleDTOById(Long id) {

        Module module = moduleRepository.findById(id).orElseThrow();
        return moduleMapper.fromEntityToListModuleDTO(module);
    }

    public FullModuleDTO getByFullModuleDTOById(Long id) {

        Module module = moduleRepository.findById(id).orElseThrow();
        return moduleMapper.fromEntityToFullModuleDTO(module);
    }

    public Module createModule(NewModuleDTO newModuleDTO) {

        Project project = projectRepository.findById(newModuleDTO.getProjectId()).orElseThrow();

        Module newModule = moduleMapper.fromNewModuleDTOToEntity(newModuleDTO);
        newModule.setProject(project);
        moduleRepository.save(newModule);
        return newModule;
    }

    public void updateModule(UpdateModuleDTO updateModuleDTO) {

        Module module = moduleRepository.findById(updateModuleDTO.getId()).orElseThrow();
        moduleMapper.updateEntityWithDTO(updateModuleDTO, module);
        moduleRepository.save(module);
    }

    public void delete(Long id) {
        Module module = moduleRepository.findById(id).orElseThrow();
        moduleRepository.delete(module);
    }
}
