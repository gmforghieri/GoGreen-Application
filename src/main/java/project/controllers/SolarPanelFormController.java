package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.UserService;
import project.view.FxmlView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

@Controller
public class SolarPanelFormController implements Initializable {

    @FXML
    private Label userLbl;

    @FXML
    private Label reCircle;

    @FXML
    private Label emLabel;

    @FXML
    private Label totalem;

    @FXML
    private TextField nmbrSolarPanel;

    @FXML
    private TextField peakPower;

    @FXML
    private DatePicker installDate;

    @FXML
    private AnchorPane mainPane;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeController homeController;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void addFood(ActionEvent event) throws IOException {

        String username = userLbl.getText();

        System.out.println("[INFO] User:" + username + " has requested to buy a vegan meal");
        ServerRequests.addFood();

        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
        reCircle.setText(2 + "g");
        emLabel.setText("CO2 emissions prevented");
    }

    @FXML
    private void localFood(ActionEvent event) throws IOException {
        String username = userLbl.getText();

        System.out.println("[INFO] User:" + username + " has requested to buy a local product");
        ServerRequests.localFood();

        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
        displayEmissions();
        reCircle.setText(4 + "g");
        emLabel.setText("CO2 emissions prevented");
    }

    @FXML
    private void addSolarPanel(ActionEvent event) throws IOException {
        String amountOfSolarPanels = getAmountOfSolarPanelsFromInput();
        String peakElectricPower = getPeakElectricPowerFromInput();

        String username = userLbl.getText();

        System.out.println("[INFO] User: " + username + " has requested to install a solar panel");
        ServerRequests.addSolarPanel(amountOfSolarPanels, peakElectricPower, getInstallDate());

        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
    }

    @FXML
    private void cancelSolarPanel(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/styles" + Theme.userTheme + ".css");
        userLbl.setText(ServerRequests.getUsername());

        DatePicker maxDate = new DatePicker();
        DatePicker minDate = new DatePicker();

        maxDate.setValue(LocalDate.now());
        minDate.setValue(LocalDate.of(2019, Month.FEBRUARY, 13));
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(maxDate.getValue()) || item.isBefore(minDate.getValue())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #cee4b4;");
                }
            }
        };
        //Finally, we just need to update our DatePicker cell factory as follow:
        installDate.setDayCellFactory(dayCellFactory);
    }

    private String getAmountOfSolarPanelsFromInput() {
        return nmbrSolarPanel.getText();
    }

    private String getPeakElectricPowerFromInput() {
        return peakPower.getText();
    }

    private String getInstallDate() {
        try {
            return installDate.getValue().toString();
        } catch (NullPointerException n) {
            n.printStackTrace();
            return null;
        }
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
