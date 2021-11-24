package com.creighbattle.serviceTests;

import com.creighbattle.domain.User;
import com.creighbattle.repositories.UserRepository;
import com.creighbattle.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userServiceTest;

    @MockBean
    private UserRepository userRepositoryTest;

    @Test
    public void findAllUserTest(){
        long userTestId = 1;
        User testUser = new User();
        testUser.setId(userTestId);
        testUser.setUsername("Test@test.com");
        testUser.setFullName("Test User");
        testUser.setPassword("password");

        when(userRepositoryTest.findAll()).thenReturn(Stream
                .of(testUser).collect(Collectors.toList()));

        Iterable<User> list=userServiceTest.findAll();

        for (User u:list) {
            System.out.println(u.getUsername());
        }

    }

    @Test
    public void saveUserTest(){
        long userTestId = 1;
        User testUser = new User();
        testUser.setId(userTestId);
        testUser.setUsername("Test@test.com");
        testUser.setFullName("Test User");
        testUser.setPassword("password");

        when(userRepositoryTest.save(Mockito.any(User.class))).thenReturn(testUser);

        User u = userServiceTest.saveUser(testUser);

        assertEquals("Test@test.com", u.getUsername());

    }

}
