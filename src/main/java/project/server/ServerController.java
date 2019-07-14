package project.server;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.service.UserDataService;
import project.service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@RestController
public class ServerController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    /**
     * This function handles the request mapping for a user going to the /login url.
     * Requires two parameters, namely the username and the password.
     * Makes a query to the database and returns the username and password if it exists.
     * @param user String[] type
     * @return a response as a String
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String[] loginResponse(@RequestBody String[] user) {
        String username = user[0];
        String password = user[1];

        if (userService.authenticate(username, password)) {

            return user;

        }
        return null;
    }

    /**
     * This function handles the request mapping for a user going to /signup url.
     * Requires two parameters, namely the username, email and hashed password.
     * It will make a query to insert a new user into the database.
     * @param newUser A String array containing the username, email and password.
     * @return the signed up user information.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String[] signupResponse(@RequestBody String[] newUser) {
        String username = newUser[0];
        String email = newUser[1];
        String password = newUser[2];


        if (!userService.exists(username)) {

            userService.userSignUp(username, email, password);
            return newUser;
        }
        return null;
    }

    /**
     * Buy vegeterian meal function correspondent.
     * @param user string array containg username.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/addFood", method = RequestMethod.POST)
    public String addFoodResponse(@RequestBody String[] user) {
        String username = user[0];
        String co2Reduction = "1000";

        userDataService.veganMeal(username, co2Reduction);
        return co2Reduction;
    }

    /**
     * Buy local products function correspondent.
     * @param user string array containg username.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/localFood", method = RequestMethod.POST)
    public String localFoodResponse(@RequestBody String[] user) {
        String username = user[0];
        String co2Reduction = "9000";

        userDataService.localFood(username, co2Reduction);
        return co2Reduction;
    }

    /**
     * Add solar panel function correspondent.
     * @param user string array containg username.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/addSolarPanel", method = RequestMethod.POST)
    public String addSolarPanelResponse(@RequestBody String[] user) {
        String username = user[0];
        String amountOfSolarPanels = user[1];
        String peakElectricPower = user[2];
        String daysInstalled = user[3];
        String dailyCo2Reduction;
        String co2Saved;

        Date installDate = Date.valueOf(daysInstalled);
        Date today = Date.valueOf(LocalDate.now());
        long dateDiff = TimeUnit.MILLISECONDS.toDays(today.getTime() - installDate.getTime());

        if (dateDiff < 0) {
            dateDiff = 0;
        }

        try {
            long dailyEnergy = Math.round((Integer.parseInt(amountOfSolarPanels)
                   * Integer.parseInt(peakElectricPower) * 931.6) / 365);
            dailyCo2Reduction = String.valueOf(Math.round(dailyEnergy * 0.526));
            co2Saved = String.valueOf(
                    Integer.parseInt(dailyCo2Reduction) * dateDiff);
        } catch (NumberFormatException e) {
            return null;
        }
        userDataService.updateSolarPanel(username, amountOfSolarPanels);
        userDataService.updateDailyCo2Reduction(username, dailyCo2Reduction, co2Saved);

        return new Gson().toJson(
                new String[]{amountOfSolarPanels, dailyCo2Reduction});
    }

    /**
     * Buy local products function correspondent.
     * @param user string array containg username.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/loweredTemp", method = RequestMethod.POST)
    public String loweredTempResponse(@RequestBody String[] user) {
        String username = user[0];
        String co2Reduction = "1500";

        userDataService.loweredTemp(username, co2Reduction);
        return co2Reduction;
    }

    /**
     * Commit to Bike function correspondent.
     * @param user string array containing username and co2 emission prevention value.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/co2ReductionBike", method = RequestMethod.POST)
    public String[] co2ReductionBike(@RequestBody String[] user) {
        String username = user[0];
        String co2Reduction = user[1];
        String distance = user[2];

        userDataService.updateDistanceTraveledByBike(username, co2Reduction, distance);
        return user;
    }

    /**
     * Commit to Public Transport function correspondent.
     * @param user string array containing username and co2 emission prevention value.
     * @return the request which has been sent in JSON format.
     */
    @RequestMapping(value = "/co2ReductionPT", method = RequestMethod.POST)
    public String[] co2ReductionPT(@RequestBody String[] user) {
        String username = user[0];
        String co2Reduction = user[1];
        String distance = user[2];

        userDataService.updateDistanceTraveledByPubTransport(username, co2Reduction, distance);
        return user;
    }

    /**
     * Displays total emissions on the mainscreen.
     * @param user string array containing username.
     * @return the totalemission value of the particular user.
     */
    @RequestMapping(value = "/displayemission", method = RequestMethod.POST)
    public String displayEmResponse(@RequestBody String[] user) {
        String username = user[0];

        String response = String.valueOf(
                Math.round(Float.parseFloat(userDataService.totalemission(username)) / 1000));
        return response;
    }

    /**
     * Displays daily co2 reduction on the mainscreen.
     * @param user string array containing the username
     * @return the dailyCo2Reduction value of the particular user.
     */
    @RequestMapping(value = "/displayDailyEmission", method = RequestMethod.POST)
    public String displayDailyEmResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.dailyEmission(username);
    }

    /**
     * Displays total emissions on the mainscreen.
     * @param user string array containing username.
     * @return the totalemission value of the particular user.
     */
    @RequestMapping(value = "/displaySolarPanels", method = RequestMethod.POST)
    public String displaySolarPanelsResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.totalSolarPanels(username);
    }

    /**
     * Displays total number of time the user has consumed a vegan meal.
     * @param user string array containing the username.
     * @return the number of vegan meals consumed by the user.
     */
    @RequestMapping(value = "/displayVeganMeals", method = RequestMethod.POST)
    public String displayVeganMealsResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.totalVeganMeals(username);
    }

    /**
     * Return the total amount of km travelled by bike.
     * @param user string array containing username.
     * @return the totalEmission value of the particular user.
     */
    @RequestMapping(value = "/displayKmTravelledByBike", method = RequestMethod.POST)
    public String displayKmTravelledByBikeResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.totalKmTravelledByBike(username);
    }

    /**
     * Return the total amount of km travelled by public transport.
     * @param user string array containing username.
     * @return the totalEmission value of the particular user.
     */
    @RequestMapping(value = "/displayKmTravelledByPubTransport", method = RequestMethod.POST)
    public String displayKmTravelledByPublicTransportResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.totalKmTravelledByPubTransport(username);
    }

    /**
     * Displays total number of time the user has consumed a local product.
     * @param user string array containing the username.
     * @return the number of local products consumed by the user.
     */
    @RequestMapping(value = "/displayLocalProducts", method = RequestMethod.POST)
    public String displayLocalProductsResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.totalLocalFood(username);
    }

    /**
     * Changes the theme of the user in the database.
     * @param user string array containing the username and theme color.
     * @return the newly set theme color.
     */
    @RequestMapping(value = "/setTheme", method = RequestMethod.POST)
    public String setThemeResponse(@RequestBody String[] user) {
        String username = user[0];
        String themeColor = user[1];

        userDataService.setTheme(username, themeColor);

        return themeColor;
    }

    /**
     * Displays the current theme color.
     * @param user string array containing the username.
     * @return the color of the current theme.
     */
    @RequestMapping(value = "/getTheme", method = RequestMethod.POST)
    public String getThemeResponse(@RequestBody String[] user) {
        String username = user[0];

        return userDataService.getTheme(username);
    }
}
