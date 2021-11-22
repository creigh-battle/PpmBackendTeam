package com.creighbattle.repositories;

import com.creighbattle.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findProjectTaskByProjectIdentifierOrderByPriority(String id);

    ProjectTask findProjectTaskByProjectSequence(String sequence);

}
