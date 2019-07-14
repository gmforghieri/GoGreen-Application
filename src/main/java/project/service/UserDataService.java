package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.bean.UserData;
import project.repository.UserDataRepository;

import java.util.List;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    public List<UserData> findAll() {
        return userDataRepository.findAll();
    }


    /**
     * This method is used to update the CO2 emissions.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2 emissions reduced.
     */
    public void updateCO2(String username, String co2Reduction) {
        UserData userData = userDataRepository.findById(username).get();

        //The splitting into multiple variables is only done because of checkstyle issues
        String co2value = String.valueOf(Integer.parseInt(userData.getCo2Reduction())
                + Integer.parseInt(co2Reduction));
        userData.setCo2Reduction(co2value);
        userDataRepository.save(userData);
    }

    /**
     * This method is used to update the amount of solar panels.
     * @param username is the username of the user.
     * @param addedSolarPanels is the amount of solar panels added.
     */
    public void updateSolarPanel(String username, String addedSolarPanels) {
        UserData userData = userDataRepository.findById(username).get();

        String solarPanels = String.valueOf(Integer.parseInt(userData.getSolarPanels())
                + Integer.parseInt(addedSolarPanels));
        userData.setSolarPanels(solarPanels);
        userDataRepository.save(userData);
    }

    /**
     * This method is used to update the distance travelled by bike.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2Reduction.
     * @param distance is the distance travelled by bike.
     */
    public void updateDistanceTraveledByBike(String username,
                                             String co2Reduction, String distance) {
        UserData userData = userDataRepository.findById(username).get();

        updateCO2(username, co2Reduction);
        String distanceByBike = String.valueOf(Double.parseDouble(userData.getKmByBike())
                + Double.parseDouble(distance));
        userData.setKmByBike(distanceByBike);
        userDataRepository.save(userData);
    }

    /**
     * This method is used to get the total amount of km travelled by bike.
     * @param username is the username of the user.
     * @return the amount of km travelled by bike.
     */
    public String totalKmTravelledByBike(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getKmByBike();
    }

    /**
     * This method is used to update the distance travelled by public transport.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2Reduction.
     * @param distance is the distance travelled by public transport.
     */
    public void updateDistanceTraveledByPubTransport(String username,
                                                     String co2Reduction, String distance) {
        UserData userData = userDataRepository.findById(username).get();

        updateCO2(username, co2Reduction);
        String distanceByPubTransport =
                String.valueOf(Double.parseDouble(userData.getKmByPublicTransport())
                + Double.parseDouble(distance));
        userData.setKmByPublicTransport(distanceByPubTransport);
        userDataRepository.save(userData);
    }

    /**
     * This method is used to get the total amount of km travelled by public transport.
     * @param username is the username of the user.
     * @return the amount of km travelled by public transport.
     */
    public String totalKmTravelledByPubTransport(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getKmByPublicTransport();
    }

    /**
     * This method is used to update the daily co2 reduction.
     * @param username is the username of the user.
     * @param addedDailyCo2Reduction is the added daily co2 reduction.
     * @param co2Reduction is the amount of co2 emissions reduced.
     */
    public void updateDailyCo2Reduction(
            String username, String addedDailyCo2Reduction, String co2Reduction) {
        UserData userData = userDataRepository.findById(username).get();

        String dailyCo2Reduction = String.valueOf(Integer.parseInt(userData.getDailyCo2reduction())
                + Integer.parseInt(addedDailyCo2Reduction));
        String co2value = String.valueOf(Integer.parseInt(userData.getCo2Reduction())
                + Integer.parseInt(co2Reduction));
        userData.setDailyCo2reduction(dailyCo2Reduction);
        userData.setCo2Reduction(co2value);
        userDataRepository.save(userData);
    }

    /**
     * This method is used to get the total amount of co2 reduction.
     * @param username is the username of the user.
     * @return the total amount of co2 reduced.
     */
    public String totalemission(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getCo2Reduction();
    }

    /**
     * This method is used to get the total amount of installed solar panels.
     * @param username is the username of the user.
     * @return the amount of installed solar panels.
     */
    public String totalSolarPanels(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getSolarPanels();
    }

    /**
     * This method is used to add a eaten vegan meal.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2 reduced by eating a vegan meal.
     */
    public void veganMeal(String username, String co2Reduction) {
        updateCO2(username, co2Reduction);
        UserData userData = userDataRepository.findById(username).get();
        userData.setVeganMeals( String.valueOf(Integer.parseInt(userData.getVeganMeals()) + 1));
        userDataRepository.save(userData);
    }

    /**
     * This method is used to return the number of vegan meals eaten by the user.
     * @param username is the username of the user.
     * @return the number of vegan meals consumed by the user.
     */
    public String totalVeganMeals(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getVeganMeals();
    }


    /**
     * This method is used to add a eaten local product.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2 reduced by buying local.
     */
    public void localFood(String username, String co2Reduction) {
        updateCO2(username, co2Reduction);
        UserData userData = userDataRepository.findById(username).get();
        userData.setLocalFood( String.valueOf(Integer.parseInt(userData.getLocalFood()) + 1));
        userDataRepository.save(userData);
    }

    /**
     * This method is used to return number of eaten local products.
     * @param username is the username of the user.
     * @return the amount of times the user bought locally produced food.
     */
    public String totalLocalFood(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getLocalFood();
    }

    /**
     * This method is used to add a day of lowered temp.
     * @param username is the username of the user.
     * @param co2Reduction is the amount of co2 reduced by lowering temperature.
     */
    public void loweredTemp(String username, String co2Reduction) {
        updateCO2(username, co2Reduction);
        UserData userData = userDataRepository.findById(username).get();
        userData.setLoweredTemp( String.valueOf(Integer.parseInt(userData.getLoweredTemp()) + 1));
        userDataRepository.save(userData);
    }

    /**
     * This method is used to get the daily co2 reduction.
     * @param username is the username of the user.
     * @return the total amount of daily co2 reduced.
     */
    public String dailyEmission(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getDailyCo2reduction();
    }

    /**
     * This method is used to get the theme of the user.
     * @param username is the username of the user.
     * @return the total amount of daily co2 reduced.
     */
    public String getTheme(String username) {
        UserData userData = userDataRepository.findById(username).get();
        return userData.getTheme();
    }

    /**
     * This method is used to update the theme in database.
     * @param username is the username of the user.
     * @param theme is the amount of co2Reduction.
     */
    public void setTheme(String username, String theme) {
        UserData userData = userDataRepository.findById(username).get();
        userData.setTheme(theme);
        userDataRepository.save(userData);
    }
}
