package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.UserDataService;
import project.service.UserService;
import project.view.FxmlView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label userLbl;

    @FXML
    private Label reCircle;

    @FXML
    private Label emLabel;

    @FXML
    private Label totalem;

    @FXML
    private Label dailyCo2Saved;

    @FXML
    private Label totalSolarP;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private SolarPanelFormController solarPanelFormController;

    @Lazy
    @Autowired
    private StageManager stageManager;

    public void setUser(String user) {
        userLbl.setText(user);
        ServerRequests.setUsername(user);
    }

    @FXML
    private void addFood(ActionEvent event) throws IOException {

        String username = userLbl.getText();

        System.out.println("[INFO] User:" + username + " has requested to buy a vegan meal");
        ServerRequests.addFood();

        displayEmissions();
        reCircle.setText(1 + "kg");
        emLabel.setText("CO2 emissions prevented\r\nby eating vegan meal");
    }

    @FXML
    private void localFood(ActionEvent event) throws IOException {
        String username = userLbl.getText();

        System.out.println("[INFO] User:" + username + " has requested to buy a local product");
        ServerRequests.localFood();

        displayEmissions();
        reCircle.setText(9 + "kg");
        emLabel.setText("CO2 emissions prevented\r\nby buying local produce");
    }

    @FXML
    private void addSolarPanel(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.SOLARPANELFORM);
        solarPanelFormController.displayEmissions();
    }

    @FXML
    private void loweredTemp(ActionEvent event) throws IOException {
        String username = userLbl.getText();

        System.out.println("[INFO] User: " + username
                + " has requested to lower their temperature");
        ServerRequests.loweredTemp();

        displayEmissions();
        reCircle.setText(1.5 + "kg");
        emLabel.setText("CO2 emissions prevented\r\nby reducing home temp");
    }

    /**
     * By calling this function the total emissions of a user is displayed.
     */
    public String displayEmissions() {
        System.out.println("[INFO] User:" + ServerRequests.getUsername()
                + " wants to display total emission prevention");

        String totalemission = ServerRequests.displayEmission();
        totalem.setText(totalemission + " kg");
        return  totalemission;
    }

    /**
     * By calling this function the total solar panels of a user is displayed.
     */
    public String displaySolarPanels() {
        System.out.println("[INFO] User: " + ServerRequests.getUsername()
                + " wants to display total amount of solar panels");
        String totalSolarPanels = ServerRequests.displaySolarPanels();
        totalSolarP.setText(totalSolarPanels);
        return totalSolarPanels;
    }

    /**
     * By calling this function the total daily co2 reductions of a user is showed.
     */
    public String displayDailyCo2() {
        System.out.println("[INFO] User: " + ServerRequests.getUsername()
                + " wants to display daily co2 reduction");
        String dailyCo2Reduction = ServerRequests.displayDailyEmission();
        dailyCo2Saved.setText(dailyCo2Reduction);
        return dailyCo2Reduction;
    }

    public String displayAll() {
        return displayEmissions() + displaySolarPanels() + displayDailyCo2();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLbl.setText(ServerRequests.getUsername());
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/styles" + Theme.userTheme + ".css");
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
