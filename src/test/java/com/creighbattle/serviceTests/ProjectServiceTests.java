package com.creighbattle.serviceTests;

import com.creighbattle.domain.Project;
import com.creighbattle.repositories.ProjectRepository;
import com.creighbattle.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTests {

    @Autowired
    ProjectService projectServiceTest;

    @Autowired
    ProjectRepository projectRepositoryTest;

    @Test
    public void findAllProjectTest(){
        long testProjId = 1;
        Project project1 = new Project();
        project1.setId(testProjId);
        project1.setProjectName("Mock Proj 1");
        project1.setProjectIdentifier("MOCK1");
        project1.setDescription("Mock project 1 description");

    }
}
