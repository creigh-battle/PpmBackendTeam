package com.creighbattle.services;

import com.creighbattle.domain.Backlog;
import com.creighbattle.domain.Project;
import com.creighbattle.domain.ProjectTask;
import com.creighbattle.exceptions.ProjectNotFoundException;
import com.creighbattle.repositories.BacklogRepository;
import com.creighbattle.repositories.ProjectRepository;
import com.creighbattle.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {


            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
                    //backlogRepository.findBacklogByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer backlogSeq = backlog.getPTSequence();
            backlogSeq++;

            backlog.setPTSequence(backlogSeq);

            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSeq);

            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }

//            if (projectTask.getPriority() == null) {
//                projectTask.setPriority(3);
//            }

            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);


    }

    public List<ProjectTask> findBacklogById(String backlog_id, String username) {

        projectService.findProjectByIdentifier(backlog_id, username);

        return projectTaskRepository.findProjectTaskByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

        projectService.findProjectByIdentifier(backlog_id, username);

        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(pt_id);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project task does not exist in project: '"
            + backlog_id + "'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }
}
