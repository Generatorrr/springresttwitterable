package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.entity.CheckList;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.checklist.FullCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.ListCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.checklist.NewCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListDTO;
import com.example.springresttwitterable.entity.dto.checklist.UpdateCheckListTestCaseDTO;
import com.example.springresttwitterable.entity.dto.testcase.ListTestCaseDTO;
import com.example.springresttwitterable.service.CheckListService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

/**
 * Created on 2020-05-14
 *
 * @author generatorr
 */

@RestController
@RequestMapping("/check-list")
public class CheckListController {

    private final CheckListService checkListService;

    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ListCheckListDTO getById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return checkListService.getById(id);
    }

    @GetMapping("{id}/full")
    @ResponseBody
    public FullCheckListDTO getFullById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return checkListService.getByFullDTOById(id);
    }

    @GetMapping("{id}/test-case/{testCaseId}")
    @ResponseBody
    public ListCheckListTestCaseDTO getTestCaseCheckListByIds(@PathVariable Long id, @PathVariable Long testCaseId,
                                                              @AuthenticationPrincipal User currentUser) {

        return checkListService.getTestCaseCheckListForEdit(id, testCaseId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@AuthenticationPrincipal User currentUser, @RequestBody @Valid NewCheckListDTO dto) {

        CheckList newEntity = checkListService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@AuthenticationPrincipal User currentUser, @RequestBody @Valid UpdateCheckListDTO dto) {

        checkListService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {

        checkListService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign/{userId}")
    public ResponseEntity assignTo(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        checkListService.assignTo(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/unassign/{userId}")
    public ResponseEntity detachFrom(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable String userId) {

        checkListService.detachFrom(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/available-test-cases")
    @ResponseBody
    public Set<ListTestCaseDTO> getAvailableTestCases(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        return checkListService.getAvailableTestCases(id);
    }

    @PutMapping("{id}/assign-test-case/{testCaseId}")
    public Integer assignTestCase(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable Long testCaseId) {

        return checkListService.assignTestCase(id, testCaseId);
    }

    @PutMapping("{id}/unassign-test-case/{testCaseId}")
    public ResponseEntity detachTestCase(@AuthenticationPrincipal User currentUser, @PathVariable Long id, @PathVariable Long testCaseId) {

        checkListService.detachTestCase(id, testCaseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/test-case/{testCaseId}")
    public ResponseEntity updateCheckListTestCase(@AuthenticationPrincipal User currentUser, @PathVariable Long id,
                                                  @PathVariable Long testCaseId, @RequestBody @Valid UpdateCheckListTestCaseDTO dto) {

        checkListService.updateCheckListTestCase(id, testCaseId, dto);
        return ResponseEntity.ok().build();
    }
}
