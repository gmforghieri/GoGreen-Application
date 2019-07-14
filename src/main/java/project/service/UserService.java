package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.bean.User;
import project.bean.UserData;
import project.repository.UserDataRepository;
import project.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    public boolean exists(String username) {
        return userRepository.existsById(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * This method is used to authenticate the user.
     * @param username is the username of the user.
     * @param password is the password of the user.
     * @return boolean authentication approval.
     */
    public boolean authenticate(String username, String password) {
        if (userRepository.existsById(username)) {
            User user = userRepository.findById(username).get();
            return password.equals(user.getPassword());
        }
        return false;
    }

    /**
     * This method is used to signup the user.
     * @param username is the username of the user.
     * @param email is the email of th user.
     * @param password is the password of the user.
     */
    public void userSignUp(String username, String email, String password) {

        User user = new User(username, email, password);
        UserData userData = new UserData(username, "0", "0", "0", "0", "0", "0", "0", "0", "Green");

        User newUser = userRepository.save(user);
        UserData newUserData = userDataRepository.save(userData);
    }
}
