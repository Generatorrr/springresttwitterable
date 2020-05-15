package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.CheckList;
import com.example.springresttwitterable.entity.TestCase;
import com.example.springresttwitterable.entity.TestCaseCheckList;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 2020-05-15
 *
 * @author generatorr
 */
public interface TestCaseCheckListRepository extends CrudRepository<TestCaseCheckList, Long> {

    TestCaseCheckList findByTestCaseAndAndCheckList(TestCase testCase, CheckList checkList);
}
