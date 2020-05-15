package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.Task;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */
public interface TaskRepository extends CrudRepository<Task, Long> {
}
