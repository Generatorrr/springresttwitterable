package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.CheckList;
import com.example.springresttwitterable.entity.Task;
import com.example.springresttwitterable.entity.TestCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */
public interface CheckListRepository extends CrudRepository<CheckList, Long> {

}
