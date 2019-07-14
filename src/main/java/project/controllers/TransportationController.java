package project.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import project.config.StageManager;
import project.server.ServerRequests;
import project.service.RouteInfo;
import project.service.TransportService;
import project.view.FxmlView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class TransportationController implements Initializable {

    @FXML
    WebView webCalculator;

    @FXML
    JFXDrawer drawerSidePanel;

    @Autowired
    private HomeController homeController;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private TransportService transportService;

    @FXML
    private ImageView imageMap;

    @FXML
    private TextField locationFrom;

    @FXML
    private TextField locationTo;

    @FXML
    private Label userLbl;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXDrawer co2InfoDrawer;

    private Boolean isCalculatorCached = false;

    private TransportCo2PanelController co2PanelController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.getStylesheets().clear();
        mainPane.getStylesheets().add("project/styles/styles" + Theme.userTheme + ".css");
        userLbl.setText(ServerRequests.getUsername());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/TransportCo2Panel.fxml"));
            VBox myBox = loader.load();
            co2InfoDrawer.setSidePane(myBox);

            //save the controller of this panel to access its attribute later
            TransportCo2PanelController controller = loader.getController();
            co2PanelController = controller.thisController;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //ensure loading the calculator again if user return back to this scene
        isCalculatorCached = false;
    }

    /**
     * This method is used to display the map in the transportation screen.
     *
     * @param event is an event.
     */
    public void handleBtnDisplayMap(ActionEvent event) {
        String apiUrl = "https://maps.googleapis.com/maps/api/staticmap?";

        String mapSize = "size=413x235&scale=2";
        String mapStyle = "style=feature:road|visibility:simplified";
        String mapType = "maptype=roadmap";
        String defaultParameter = mapSize + "&" + mapStyle + "&" + mapType;

        String mapMarkerStyle = "markers=color:green|size:small";
        String mapMarkerLocFrom = locationFrom.getText();
        String mapMarkerLocTo = locationTo.getText();
        String locations = mapMarkerStyle + "|" + mapMarkerLocFrom + "|" + mapMarkerLocTo;

        String imgUrl = transportService.apiParser(apiUrl, defaultParameter, locations, null);
        System.out.println(imgUrl);
        Image image = new Image(imgUrl);

        imageMap.setImage(image);

        if (co2InfoDrawer.isClosed()) {
            co2InfoDrawer.open();
        }

        updateCo2PanelLabel(locationFrom.getText(), locationTo.getText());
        co2PanelController.isCommitedBike = false;
        co2PanelController.isCommitedPT = false;
        co2PanelController.btnCommitBike.setText("Commit to Bike");
        co2PanelController.btnCommitPT.setText("Commit to PT");
    }

    /**
     * Display the calculated info of distance,
     * duration and co2 emission for the given trip specified by two locations.
     *
     * @param locationFrom the start point of the trip
     * @param locationTo   the end point of the trip
     */
    public void updateCo2PanelLabel(String locationFrom, String locationTo) {
        RouteInfo directionPT = transportService
                .getRouteInfo(locationFrom, locationTo, "transit");
        RouteInfo directionBike = transportService
                .getRouteInfo(locationFrom, locationTo, "bicycling");

        //update distance
        co2PanelController.distanceBike.setText(directionBike.getDistanceText());
        co2PanelController.distancePT.setText(directionPT.getDistanceText());

        //update travel time
        co2PanelController.timeBike.setText(directionBike.getTimeText());
        co2PanelController.timePT.setText(directionPT.getTimeText());


        //update co2 reduction in case user take bike instead of his car
        //therefore it is simply equal to co2 emission from his car
        RouteInfo directionCar = transportService.getRouteInfo(locationFrom, locationTo, "driving");
        double co2EmissionUsingCar = transportService
                .co2EmissionUsingCar(directionCar.getDistance());
        co2PanelController.co2ReductionBike.setText(co2EmissionUsingCar + " kg");

        //update co2 reduction in case user take Public Transport instead of his car
        //therefore it is simply equal to co2
        // emission from his car - the co2 emission from Public Transport
        double co2ReductionUsingPT =
                Math.floor((co2EmissionUsingCar - transportService
                        .co2EmissionUsingPT(directionPT.getDistance())) * 100) / 100;
        co2PanelController.co2ReductionPT.setText(co2ReductionUsingPT + " kg");

        co2PanelController.mapDirectionUrl = transportService.getGoogleMapDirectionLink(
                locationFrom, locationTo, "transit");
    }

    /**
     * load the advance calculator when its tab is selected.
     */
    public void handleTabCalculator() {
        if (!isCalculatorCached) {
            webCalculator.getEngine()
                    .loadContent("<iframe width=\"810\" height=\"700\" "
                            + "frameborder=\"0\" marginwidth=\"0\""
                            + " marginheight=\"0\" scrolling=\"no\" "
                            + "src=\"https://calculator.carbonfootprint.com/calculator.aspx\"></iframe>");
            isCalculatorCached = true;
            System.out.println("-- the advance calculator is loaded/cached");
        }
    }

    /**
     * this method change the scene to Home page.
     */
    public void handleBtnHome(ActionEvent event) {
        stageManager.switchScene(FxmlView.HOME);
        homeController.displayAll();
    }

    /**
     * this method change the scene to Logout page.
     */
    public void handleBtnLogout(ActionEvent event) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    /**
     * this method change the scene to Profile page.
     */
    public void handleBtnProfile(ActionEvent event) {
        stageManager.switchScene(FxmlView.PROFILE);
    }

    /**
     * this method change the scene to Leaderboard page.
     */
    public void handleBtnLeaderboard(ActionEvent event) {
        stageManager.switchScene(FxmlView.LEADERBOARD);
    }
}
