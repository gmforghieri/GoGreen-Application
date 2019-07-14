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
import project.service.UserDataService;
import project.view.FxmlView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @Autowired
    private HomeController homeController;

    @Autowired
    private UserDataService userDataService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label loginstatus;

    /**
     * This method is used to log in into the application.
     * @param event is an event that the user activates.
     * @throws IOException Surprise, surprise.
     */
    @FXML
    public void login(ActionEvent event) throws IOException {
        String username = getUsernameFromInput();
        String password = getPasswordFromInput();

        System.out.println("[INFO] User:" + username + " has requested login");
        String response = ServerRequests.login(username, password);
        if (response == null) {
            //USERNAME OR PASSWORD MISSING
            loginstatus.setText("Missing information");
        } else if (response.equals("fail")) {
            //WRONG USERNAME OR PASSWORD
            loginstatus.setText("Login Failed.");
        } else if (response.startsWith("success:")) {
            //GET THE THEME FROM DATABASE FOR THIS USER
            Theme.userTheme = userDataService.getTheme(username);

            //GOTO MAIN SCREEN
            stageManager.switchScene(FxmlView.HOME);
            homeController.setUser(getUsernameFromInput());
            homeController.displayAll();
        }
    }

    @FXML
    private void signup(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.SIGNUP);
    }

    public String getPasswordFromInput() {
        return password.getText();
    }

    public String getUsernameFromInput() {
        return username.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/stylesGreen.css");
    }

}
