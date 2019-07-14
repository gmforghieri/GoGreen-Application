package project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.bean.UserData;
import project.repository.UserDataRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserDataServiceTest {

    @Autowired
    UserDataService userDataService;

    @Autowired
    UserDataRepository userDataRepository;

    String username = "junit";
    UserData user = new UserData(username, "0" , "0", "0", "0", "0", "0", "0", "0", "green");

    @Test
    public void updateCo2() {
        user.setCo2Reduction("0");
        userDataRepository.save(user);
        userDataService.updateCO2(username, "2");
        assertEquals("2", userDataRepository.findById(username).get().getCo2Reduction());
    }

    @Test
    public void updateSolarPanel() {
        user.setSolarPanels("0");
        userDataRepository.save(user);
        userDataService.updateSolarPanel(username, "2");
        assertEquals("2", userDataRepository.findById(username).get().getSolarPanels());
    }

    @Test
    public void updateDailyCo2Reduction() {
        user.setCo2Reduction("0");
        user.setDailyCo2reduction("0");
        userDataRepository.save(user);
        userDataService.updateDailyCo2Reduction(username, "5", "8");
        assertEquals("Checking if daily co2 reduction is correct", "5",
                userDataRepository.findById(username).get().getDailyCo2reduction());
        assertEquals("Checking if total co2 reduction is correct", "8",
                userDataRepository.findById(username).get().getCo2Reduction());
    }

    @Test
    public void totalEmissions() {
        user.setCo2Reduction("400");
        userDataRepository.save(user);
        assertEquals("400", userDataService.totalemission(username));
    }

    @Test
    public void totalSolarPanel() {
        user.setSolarPanels("15");
        userDataRepository.save(user);
        assertEquals("15", userDataService.totalSolarPanels(username));
    }

    @Test
    public void veganMeal() {
        user.setVeganMeals("3");
        userDataRepository.save(user);
        userDataService.veganMeal(username, "2");
        assertEquals("4", userDataRepository.findById(username).get().getVeganMeals());
    }

    @Test
    public void localFood() {
        user.setLocalFood("0");
        userDataRepository.save(user);
        userDataService.localFood(username, "4");
        assertEquals("1", userDataRepository.findById(username).get().getLocalFood());
    }

    @Test
    public void loweredTemp() {
        user.setLoweredTemp("0");
        userDataRepository.save(user);
        userDataService.loweredTemp(username, "7");
        assertEquals("1", userDataRepository.findById(username).get().getLoweredTemp());
    }

    @Test
    public void showDailyCo2Reduction() {
        user.setDailyCo2reduction("201");
        userDataRepository.save(user);
        assertEquals("201", userDataService.dailyEmission(username));
    }

    @Test
    public void getTheme() {
        user.setTheme("pink");
        userDataRepository.save(user);
        assertEquals("pink", userDataService.getTheme(username));
    }

    @Test
    public void setTheme() {
        user.setTheme("green");
        userDataRepository.save(user);
        userDataService.setTheme(username, "blue");
        assertEquals("blue", userDataRepository.findById(username).get().getTheme());
    }
}
