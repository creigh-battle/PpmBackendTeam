package com.creighbattle.repositories;

import com.creighbattle.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findBacklogByProjectIdentifier(String identifier);

}
