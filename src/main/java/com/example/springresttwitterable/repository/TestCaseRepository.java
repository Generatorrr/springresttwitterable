package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.TestCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */
public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    @Query("select tc from TestCase tc join tc.requirement r where r.module.id = ?1")
    Set<TestCase> findByModuleId(Long id);
}
