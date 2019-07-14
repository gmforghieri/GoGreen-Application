package project.controllers;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.UserDataService;
import project.view.FxmlView;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ProfileController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private HomeController homeController;

    @Autowired
    private UserDataService userDataService;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox achievementVbox;

    @FXML
    private Label userLbl;

    @FXML
    private Label solarPanelsLbl;

    @FXML
    private Label totalEmissionsLbl;

    @FXML
    private Label veganMealsLbl;

    @FXML
    private Label localProductsLbl;

    @FXML
    private Label kmByBikeLbl;

    @FXML
    private Label kmByPubTransLbl;

    @FXML
    private void handleBtnTransportation(ActionEvent event) {
        stageManager.switchScene(FxmlView.TRASPORTATION);
    }

    @FXML
    private void handleBtnLeaderboard(ActionEvent event) {
        stageManager.switchScene(FxmlView.LEADERBOARD);
    }

    @FXML
    private void handleBtnLogout(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private void handleBtnHome(ActionEvent event) {
        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
    }

    /**
     * This method is used to display all achievements acquired by the user.
     */
    private void allAchievements() {
        System.out.println("[INFO] User:" + ServerRequests.getUsername()
                + " wants to display all acquired achievements");

        reductionAchievements();
        energyAchievements();
        foodAchievements();
        transportationAchievements();
    }

    /**
     * This method handles all achievements related to transportation.
     */
    public void transportationAchievements() {

        double totalKmByBike = parseDouble(ServerRequests.displayKmTravelledByBike());
        double totalKmByPubTrans = parseDouble(ServerRequests.displayKmTravelledByPubTransport());

        if (totalKmByBike + totalKmByPubTrans >= 100) {
            displayAchievement("Green commuter", "100 'green' kilometers",
                    "project/images/achievementBadges/100greenKilometers.png", "#dca570");
        }
    }

    /**
     * This method handles all achievements related to transportation.
     */
    public void foodAchievements() {
        int totalVeganMeals = parseInt(ServerRequests.displayVeganMeals());

        if (totalVeganMeals >= 10 && totalVeganMeals < 100) {
            displayAchievement("Starting a new lifestyle", "10 vegan meals",
                    "project/images/achievementBadges/10veganMeals.png", "#dca570");
        } else if (totalVeganMeals >= 100 && totalVeganMeals < 500) {
            displayAchievement("Keeping it animal friendly", "100 vegan meals",
                    "project/images/achievementBadges/100veganMeals.png", "#C0C0C0");
        } else if (totalVeganMeals >= 500) {
            displayAchievement("Vegan master class", "500 vegan meals",
                    "project/images/achievementBadges/500veganMeals.png", "#FFDF00");
        }

        int totalLocalProducts = parseInt(ServerRequests.displayLocalProducts());

        if (totalLocalProducts >= 10 && totalLocalProducts < 500) {
            displayAchievement("Naturally green", "10 local products",
                    "project/images/achievementBadges/10localProducts.png", "#dca570");
        } else if (totalLocalProducts >= 500) {
            displayAchievement("Local product ambassador", "500 local products",
                    "project/images/achievementBadges/500localProducts.png", "#FFDF00");
        }
    }

    /**
     * This method handles all achievements related to energy.
     */
    public void energyAchievements() {
        int solarPanels = parseInt(ServerRequests.displaySolarPanels());

        if (solarPanels >= 1 && solarPanels < 5) {
            displayAchievement("First steps toward energy independence", "1 solar panel",
                    "project/images/achievementBadges/1solarPanel.png", "#C0C0C0");
        } else if (solarPanels >= 5) {
            displayAchievement("Only clean energy", "5 solar panels",
                    "project/images/achievementBadges/5solarPanels.png", "#FFDF00");
        }
    }

    /**
     * This method handles all reduction related achievements.
     */
    public void reductionAchievements() {
        int totalReduction = parseInt(ServerRequests.displayEmission());

        if (totalReduction >= 1000 && totalReduction < 10000) {
            displayAchievement("Step by step", "1000gr of CO2",
                    "project/images/achievementBadges/1000EmRed.png", "#dca570");
        } else if (totalReduction >= 10000 && totalReduction < 100000) {
            displayAchievement("Getting the hang of it", "10kg of CO2",
                    "project/images/achievementBadges/10000EmRed.png", "#C0C0C0");
        } else if (totalReduction >= 100000) {
            displayAchievement("Saviour of the planet", "100kg of CO2",
                    "project/images/achievementBadges/100000EmRed.png", "#FFDF00");
        }
    }

    /**
     * The method is used to generate new achievement in the profile screen.
     * @param title is used in order to provide description for the achievement
     * @param imgUrl is used to pass the path for the badge to be displayed
     */
    @FXML
    public void displayAchievement(String title, String desc, String imgUrl, String border) {
        final HBox achievement = new HBox();
        final VBox achievementTxt = new VBox();
        final Label achievementTitle = new Label();
        final Label achievementDesc = new Label();
        final Pane achievementPic = new Pane();
        final ImageView iv1 = new ImageView();
        iv1.setImage(new Image(imgUrl, true));


        achievement.setPrefHeight(100.0);
        achievement.setMinHeight(100.0);
        achievement.setPrefWidth(400.0);
        achievement.setMinWidth(400.0);
        achievement.setMaxHeight(100.0);
        achievement.setMaxWidth(400.0);
        achievement.getStyleClass().add("card");
        achievement.setPadding(new Insets(3, 5, 5, 20));
        achievement.setBorder(new Border(new BorderStroke(Paint.valueOf(border),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        achievementTxt.setPrefWidth(260.0);

        achievementTitle.setPrefWidth(260.0);
        achievementTitle.setPrefHeight(60.0);
        achievementTitle.setWrapText(true);
        achievementTitle.setText(title);
        achievementTitle.setAlignment(Pos.CENTER_LEFT);
        achievementTitle.getStyleClass().add("title");

        achievementDesc.setPrefWidth(240.0);
        achievementDesc.setPrefHeight(30.0);
        achievementDesc.setWrapText(true);
        achievementDesc.setText(desc);
        achievementDesc.setAlignment(Pos.CENTER_LEFT);
        achievementDesc.setPadding(new Insets(0, 0, 10, 0));
        achievementDesc.getStyleClass().add("subtext_card");

        achievementPic.setPrefHeight(85.0);
        achievementPic.setPrefWidth(85.0);
        iv1.setFitHeight(90.0);
        iv1.setFitWidth(90.0);
        achievementPic.getChildren().add(iv1);

        achievementTxt.getChildren().addAll(achievementTitle, achievementDesc);
        achievement.getChildren().addAll(achievementTxt, achievementPic);
        achievementVbox.getChildren().addAll(achievement);
        VBox.setMargin(achievement, new Insets(20, 0, 0, 42));

    }

    @FXML
    void btnThemeBlue(ActionEvent event) {
        Theme.userTheme = "Blue";
        stageManager.switchScene(FxmlView.PROFILE);
        ServerRequests.setTheme(Theme.userTheme);
    }

    @FXML
    void btnThemeGreen(ActionEvent event) {
        Theme.userTheme = "Green";
        stageManager.switchScene(FxmlView.PROFILE);
        ServerRequests.setTheme(Theme.userTheme);
    }

    @FXML
    void btnThemePink(ActionEvent event) {
        Theme.userTheme = "Pink";
        stageManager.switchScene(FxmlView.PROFILE);
        ServerRequests.setTheme(Theme.userTheme);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/styles" + Theme.userTheme + ".css");
        userLbl.setText(ServerRequests.getUsername());
        totalEmissionsLbl.setText(ServerRequests.displayEmission());
        solarPanelsLbl.setText(ServerRequests.displaySolarPanels());
        veganMealsLbl.setText(ServerRequests.displayVeganMeals());
        localProductsLbl.setText(ServerRequests.displayLocalProducts());
        kmByBikeLbl.setText(ServerRequests.displayKmTravelledByBike());
        kmByPubTransLbl.setText(ServerRequests.displayKmTravelledByPubTransport());
        allAchievements();
    }

}
