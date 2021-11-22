package com.creighbattle.web;

import com.creighbattle.domain.Project;
import com.creighbattle.domain.ProjectTask;
import com.creighbattle.repositories.ProjectTaskRepository;
import com.creighbattle.services.MapValidationErrorService;
import com.creighbattle.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBL(@Valid @RequestBody ProjectTask projectTask,
                                       BindingResult result, @PathVariable String backlog_id,
                                       Principal principal) {

        ResponseEntity<?> errorMAp = mapValidationErrorService.MapValidationService(result);
        if (errorMAp != null) return errorMAp;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

    }

    @GetMapping("/{backlog_id}")
    public ResponseEntity<?> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return new ResponseEntity<>(projectTaskService.findBacklogById(backlog_id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ResponseEntity<?> errorMAp = mapValidationErrorService.MapValidationService(result);
        if (errorMAp != null) return errorMAp;

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<>("Project Task " + pt_id + " was deleted successfully", HttpStatus.OK);

    }
}
