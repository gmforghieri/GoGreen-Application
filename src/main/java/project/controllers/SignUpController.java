package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.UserService;
import project.view.FxmlView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SignUpController implements Initializable {

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label sustatus;

    @FXML
    public void login(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * This method is used to sign up a new user.
     * @param event is an event activated by the user
     * @throws IOException Surprise, surprise.
     */
    @FXML
    public void signup(ActionEvent event) throws IOException {


        String username = getUsernameFromInput();
        String email = getEmailFromInput();
        String password = getPasswordFromInput();

        String response = ServerRequests.signUp(username, email, password);
        if (response == null) {
            //USERNAME, EMAIL, OR PASSWORD MISSING
            sustatus.setText("Missing fields");
        } else if (response.equals("fail")) {
            //SIGN UP WAS UNSUCCESSFUL
            sustatus.setText("Username already exists");
        } else if (response.equals("syntax")) {
            //SIGN UP WAS UNSUCCESSFUL
            sustatus.setText("Invalid or missing characters in"
                    + " username or email");
        } else if (response.equals("ok")) {
            //GOTO MAIN SCREEN
            sustatus.setText("Sign Up Successful");

        }
    }

    public String getUsernameFromInput() {
        return username.getText();
    }

    public String getEmailFromInput() {
        return email.getText();
    }

    public String getPasswordFromInput() {
        return password.getText();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
