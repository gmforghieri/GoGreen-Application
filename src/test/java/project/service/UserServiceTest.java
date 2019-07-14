package project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.bean.User;
import project.repository.UserRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void exists() {
        User user = new User("junit", "junit@gmail.com" , "junit");
        userRepository.save(user);
        assertTrue(userService.exists("junit"));
    }

    @Test
    public void UserExist() {
        User user = new User("junit", "junit@gmail.com" , "junit");
        userRepository.save(user);
        assertTrue(userService.authenticate(user.getUsername(), user.getPassword()));
    }

    @Test
    public void UserExistsWrongPass() {
        User user = new User("none", "none@gmail.com", "none");
        assertFalse(userService.authenticate(user.getUsername(), user.getPassword()));
    }

    @Test
    public void signUp() {
        userService.userSignUp("junit", "junit@gmail.com" , "junit");
        assertTrue(userRepository.existsById("junit"));
    }

}
