package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.bean.UserData;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.UserDataService;
import project.view.FxmlView;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LeaderboardController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label userLbl;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private HomeController homeController;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private TableView<UserData> userTable;

    @FXML
    private TableColumn<UserData, String> colUsername;

    @FXML
    private TableColumn<UserData, String> colCO2;

    private ObservableList<UserData> userList = FXCollections.observableArrayList();

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

    /**
     *  Add All users to observable list and update table.
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userDataService.findAll());

        userTable.setItems(userList);
    }

    /*
     *  Set All userTable column properties
     */
    private void setColumnProperties() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colCO2.setCellValueFactory(new PropertyValueFactory<>("co2Reduction"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLbl.setText(ServerRequests.getUsername());
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/styles" + Theme.userTheme + ".css");

        setColumnProperties();
        loadUserDetails();
    }
}
