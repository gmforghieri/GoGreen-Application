package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.view.FxmlView;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SidePanelController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private HomeController homeController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** this method change the scene to Home page.
     */
    public void handleBtnHome(ActionEvent event) {
        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
    }

    /** this method change the scene to Logout page.
     */
    public void handleBtnLogout(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /** this method change the scene to Transportation page.
     */
    public void handleBtnTransportation(ActionEvent event) {
        stageManager.switchScene(FxmlView.TRASPORTATION);
    }

    /** this method change the scene to Profile page.
     */
    public void handleBtnProfile(ActionEvent event) {
        stageManager.switchScene(FxmlView.PROFILE);
    }

    /** this method change the scene to Leaderboard page.
     */
    public void handleBtnLeaderboard(ActionEvent event) {
        stageManager.switchScene(FxmlView.LEADERBOARD);
    }
}
