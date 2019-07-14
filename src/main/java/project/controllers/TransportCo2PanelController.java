package project.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.springframework.stereotype.Controller;
import project.server.ServerRequests;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class TransportCo2PanelController implements Initializable {

    @FXML
    protected Label distanceBike;

    @FXML
    protected Label timeBike;

    @FXML
    protected Label co2ReductionBike;

    @FXML
    protected Label distancePT;

    @FXML
    protected Label timePT;

    @FXML
    protected Label co2ReductionPT;

    @FXML
    protected JFXButton btnCommitBike;

    @FXML
    protected JFXButton btnCommitPT;

    protected Boolean isCommitedBike = false;
    protected Boolean isCommitedPT = false;

    protected String mapDirectionUrl = "";

    // Can't get the @FXML attributes directly
    // without using a Non-FXML attribute first to specify the class
    protected TransportCo2PanelController thisController = this;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isCommitedBike = false;
        isCommitedPT = false;
    }

    /** this method send request to server to add the CO2 reduction of using bike to user account.
     */
    @FXML
    public void handleCommitBike(ActionEvent event) {

        // prevent multi commits if user pressed the button multiple times
        if (isCommitedBike) {
            copyToClipBoard(mapDirectionUrl.replace("transit", "bicycling"));
            return;
        }

        double co2ReductionKg =
                Double.parseDouble(this.co2ReductionBike.getText().split(" ")[0]) * 1000;
        String co2ReductionGram = String.valueOf((int) co2ReductionKg);
        System.out.println(co2ReductionGram);

        double distance =
                Double.parseDouble(this.distanceBike.getText().split(" ")[0]);
        Math.round(distance);
        String distanceByBike = String.valueOf(distance);

        ServerRequests.co2ReductionBike(
                co2ReductionGram, distanceByBike);

        copyToClipBoard(mapDirectionUrl.replace("transit", "bicycling"));
        btnCommitBike.setText("Copy Direction Link");
        isCommitedBike = true;
    }

    /** this method send request to server to add
     *  the CO2 reduction of using Public Transport to user account.
     */
    @FXML
    public void handleCommitPT(ActionEvent event) {

        // prevent multi commits if user pressed the button multiple times
        if (isCommitedPT) {
            copyToClipBoard(mapDirectionUrl);
            return;
        }

        double co2ReductionKg =
                Double.parseDouble(this.co2ReductionPT.getText().split(" ")[0]) * 1000;
        String co2ReductionGram =
                String.valueOf((int) co2ReductionKg);
        System.out.println(co2ReductionGram);

        double distance =
                Double.parseDouble(this.distancePT.getText().split(" ")[0]);
        Math.round(distance);
        String distanceByPT = String.valueOf(distance);

        ServerRequests.co2ReductionPT(
                co2ReductionGram, distanceByPT);

        copyToClipBoard(mapDirectionUrl);
        btnCommitPT.setText("Copy Direction Link");
        isCommitedPT = true;
    }

    /**
     * this method take a string and copy it to clipboard of the system.
     *
     * @param toBeCopied the string to be copied to the system clip board
     */
    public void copyToClipBoard(String toBeCopied) {
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(toBeCopied);
        cb.setContent(content);
    }
}
