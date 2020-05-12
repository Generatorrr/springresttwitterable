package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Page<Project> findAll(Pageable pageable);
    Page<Project> findByNameContaining(String filter, Pageable pageable);
}
