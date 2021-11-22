package com.creighbattle.services;

import com.creighbattle.domain.Backlog;
import com.creighbattle.domain.Project;
import com.creighbattle.domain.User;
import com.creighbattle.exceptions.ProjectIdException;
import com.creighbattle.exceptions.ProjectNotFoundException;
import com.creighbattle.repositories.BacklogRepository;
import com.creighbattle.repositories.ProjectRepository;
import com.creighbattle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        try {

            if (project.getId() != null) {
                Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

                if (existingProject != null && !existingProject.getProjectLeader().equals(username)) {
                    throw new ProjectNotFoundException("Project not found in your account");
                } else if(existingProject ==null) {
                    throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() +
                            "' cannot be updated because it does not exist.");
                }
            }

            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            } else {
                project.setBacklog(backlogRepository.findBacklogByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()
                    + "' Already Exists");
        }

    }

    public Project findProjectByIdentifier(String projectId, String username) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project does not exist");
        }

        if(!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }


}
