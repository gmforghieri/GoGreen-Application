package project.server;

import com.google.gson.Gson;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.regex.Pattern;

public class ServerRequests {

    private static String username;

    private static String requestUrl = "http://localhost:3000/";

    public ServerRequests(String username) {
        this.username = username;
    }

    public static void setUsername(String user) {
        username = user;
    }

    public static String getUsername() {
        return  username;
    }

    /**
     * This function takes in a username and password, both of type String.
     * If the server responds with the correct result it will print this to the console,
     * and processes the login.
     * if not valid it will print "Wrong username or password".
     *
     * @param username type.
     * @param password type.
     */
    public static String login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        String response = sendRequestToServer("login",
                new Gson().toJson(new String[]{username, password}));

        if (response != null) {
            String[] resArr = response.split("::");
            System.out.println("[INFO] User:" + username + " has logged in.");
            return "success: " + resArr[0];
        } else {
            System.out.println("[ERROR] Wrong username or password");
            return "fail";
        }
    }

    /**
     * This function takes in a username and password, both of type String.
     * If the username and/or password don't have the proper syntax it will return.
     * If the server responds with the correct result it will print this to the console,
     * if not valid it will print an error notifying the sign up was not successful.
     *
     * @param username parameter.
     * @param email    parameter.
     * @param password parameter.
     * @return the input in JSON format.
     */
    public static String signUp(String username, String email, String password) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        Pattern emailPattern =
                Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
        if (username == null || email == null || password == null) {
            return null;
        }

        if (!pattern.matcher(username).matches() || !emailPattern.matcher(email).matches()
                || !pattern.matcher(password).matches()) {
            return "syntax";
        }

        String response = sendRequestToServer("signup",
                new Gson().toJson(new String[]{username, email, password}));

        if (response != null) {
            System.out.println("[INFO] The sign up was successful: " + response);
            return "ok";
        } else {
            System.out.println("[ERROR] The sign up was not successful");
            return "fail";
        }
    }

    /**
     * This function is being called when the user presses the addfood button,
     * which reduces co emission.
     * Same idea of functionality as the previous functions.
     *
     * @return the username and co2reduction in JSON format.
     */
    public static String addFood() {
        String request = new Gson().toJson(
                new String[]{username});

        System.out.println("[INFO] The User: " + username + " sent the following request "
                + request + " with the add food request");

        String response = sendRequestToServer("addFood", request);
        String[] resArr = response.split("::");

        System.out.println("[INFO] User:" + username + " has bought a vegan meal and "
                + "prevented co2 emission by " + resArr[0]);

        return resArr[0];
    }

    /**
     * This function is being called when the user presses the local food button,
     * which reduces co2 emission.
     * Same idea of functionality as the previous functions.
     *
     * @return the username and co2reduction in JSON format.
     */
    public static String localFood() {
        String request = new Gson().toJson(
                new String[]{username});

        System.out.println("[INFO] The User: " + username + " sent the following request "
                + request + " with the local food request");

        String response = sendRequestToServer("localFood", request);
        String[] resArr = response.split("::");

        System.out.println("[INFO] User:" + username + " has bought a local product and "
                + "prevented co2 emission by " + resArr[0]);

        return resArr[0];
    }

    /**
     * This function is being called when the user presses the addSolarPanel button,
     * which adds to the amount of solar panels and reduces co2 emissions.
     * Same idea of functionality as the previous functions.
     *
     * @return the username, co2reduction and solar panels in JSON format.
     */
    public static String addSolarPanel(String amountOfSolarPanels,
                                       String peakElectricPower, String daysSinceInstall) {
        if (amountOfSolarPanels == null || peakElectricPower == null || daysSinceInstall == null) {
            System.out.println("[ERROR] missing input");
            return null;
        }

        String request = new Gson().toJson(
                new String[]{username, amountOfSolarPanels,
                    peakElectricPower, daysSinceInstall});

        System.out.println("[INFO] The User: " + username + " sent the following request "
                + request + " with the add solar panel request");

        String response = sendRequestToServer("addSolarPanel", request);
        if (response != null) {
            String[] resArr = response.split("::");
            System.out.println("[INFO] User: " + username + " has installed a solar panel "
                    + "and prevented co2 emission by " + resArr[0]);
            return resArr[0];
        } else {
            System.out.println("[ERROR] invalid input");
            return "fail";
        }
    }

    /**
     * This function is being called when the user presses the lowered temperature button,
     * which reduces the co2 emissions.
     * Same idea of functionality as the previous functions.
     *
     * @return the username and co2 reduction in JSON format.
     */
    public static String loweredTemp() {
        String request = new Gson().toJson(
                new String[]{username});

        System.out.println("[INFO] The User: " + username + " sent the following request "
                + request + " with the lowered temperature request");

        String response = sendRequestToServer("loweredTemp", request);
        String[] resArr = response.split("::");

        System.out.println("[INFO] User: " + username + " has lowered temperature "
                + "and prevented co2 emission by " + resArr[0]);

        return  resArr[0];
    }

    /**
     * This function is being called when the user presses the Commit to Bike button,
     * which reduces co emission.
     * Same idea of functionality as the previous functions.
     *
     * @param co2Reduction parameter.
     * @return the username and co2reduction in JSON format.
     */
    public static String co2ReductionBike(String co2Reduction, String distanceBike) {

        String response = sendRequestToServer("co2ReductionBike",
                new Gson().toJson(new String[]{username, co2Reduction, distanceBike}));
        String[] resArr = response.split("::");

        return resArr[0];
    }

    /**
     * This function is being called when the user presses the Commit to PT button,
     * which reduces co emission.
     * Same idea of functionality as the previous functions.
     *
     * @param co2Reduction parameter.
     * @return the username and co2reduction in JSON format.
     */
    public static String co2ReductionPT(String co2Reduction, String distancePt) {

        String response = sendRequestToServer("co2ReductionPT",
                new Gson().toJson(new String[]{username, co2Reduction, distancePt}));
        String[] resArr = response.split("::");

        return resArr[0];
    }


    /**
     * This function is being called whenever there is an action
     * which affects the co2 emission of the user.
     * This function updates the homescreen total co2 emission counter.
     *
     * @return the total co2 emission;
     */
    public static String displayEmission() {

        String response = sendRequestToServer("displayemission",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of solar panels of the user.
     * This function updates the homescreen total solar panels counter.
     *
     * @return the total solar panels.
     */
    public static String displaySolarPanels() {
        String response = sendRequestToServer("displaySolarPanels",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of vegan meals consumed by the user.
     * This function updates the profilescreen total vegan meals counter.
     *
     * @return the total vegan meals.
     */
    public static String displayVeganMeals() {
        String response = sendRequestToServer("displayVeganMeals",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of local products consumed by the user.
     * This function updates the profilescreen total local products counter.
     *
     * @return the total local products.
     */
    public static String displayLocalProducts() {
        String response = sendRequestToServer("displayLocalProducts",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of km the user has gone by bike.
     * This function updates the profilescreen total km by bike counter.
     *
     * @return the total km by bike.
     */
    public static String displayKmTravelledByBike() {
        String response = sendRequestToServer("displayKmTravelledByBike",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of km the user has gone by public transport.
     * This function updates the profilescreen total km by public transport counter.
     *
     * @return the total km by public transport.
     */
    public static String displayKmTravelledByPubTransport() {
        String response = sendRequestToServer("displayKmTravelledByPubTransport",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function is being called whenever there is an action
     * which affects the amount of daily co2 reduction of the user.
     * This function updates the homescreen daily co2 reduction.
     *
     * @return the daily co2 reduction.
     */
    public static String displayDailyEmission() {
        String response = sendRequestToServer("displayDailyEmission",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function will update the theme of the user in the database.
     *
     * @param color the color of the updated theme.
     * @return the newly set theme color.
     */
    public static String setTheme(String color) {
        String response = sendRequestToServer("setTheme",
                new Gson().toJson(new String[]{username, color}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function will show the current theme of the user.
     *
     * @return the current theme of the user.
     */
    public static String getTheme() {
        String response = sendRequestToServer("getTheme",
                new Gson().toJson(new String[]{username}));

        String[] resArr = response.split("::");
        return resArr[0];
    }

    /**
     * This function will prepare the HTTP client to send a request to the server.
     * With type it will know what URL to go to. The response will be a String,
     * which can be formatted afterwards via JSON.
     *
     * @param type is the specified url.
     * @param json is the user in json format.
     * @return a string which will be formatted as JSON.
     */
    public static String sendRequestToServer(String type, String json) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(requestUrl + type);

            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            String msg = new BasicResponseHandler().handleResponse(response);
            client.close();
            if (msg.length() < 1) {
                throw new IOException();
            }
            System.out.println("[INFO] The server responded with " + msg);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
