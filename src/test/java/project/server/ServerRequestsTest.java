package project.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.bean.User;
import project.bean.UserData;
import project.repository.UserDataRepository;
import project.repository.UserRepository;
import project.service.UserDataService;
import project.service.UserService;

import java.time.LocalDate;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ServerRequestsTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Test
    public void getUsername() {
        ServerRequests testServerRequest = new ServerRequests("junit");
        assertEquals("junit", testServerRequest.getUsername());
    }

    @Test
    public void setUsername() {
        ServerRequests testServerRequest = new ServerRequests("");
        testServerRequest.setUsername("junit");
        assertEquals("junit", testServerRequest.getUsername());
    }

    @Test
    public void loginUsernameNull() {

        assertNull(ServerRequests.login(null, "123"));
    }

    @Test
    public void loginPasswordNull() {

        assertNull(ServerRequests.login("test", null));
    }

    @Test
    public void loginUsernameWrong() {
        String response = ServerRequests.login("usernamewrong", "junit");
        assertEquals("fail", response);
    }


    @Test
    public void loginPasswordWrong() {
        String response = ServerRequests.login("junit", "passwordwrong");
        assertEquals("fail", response);
    }

    @Test
    public void loginCorrect() {
        String response = ServerRequests.login("junit", "junit");
        assertTrue(response.startsWith("success:"));
    }


    @Test
    public void signUpUsernameNull() {
        assertNull(ServerRequests.signUp(null, "test@gmail.com", "123"));
    }

    @Test
    public void signUpEmailNull() {
        assertNull(ServerRequests.signUp("test", null, "123"));
    }

    @Test
    public void signUpPasswordNull() {
        assertNull(ServerRequests.signUp("test", "test@gmail.com", null));
    }

    @Test
    public void signUpUserSyntax() {
        String response = ServerRequests.signUp("test!!!!!!", "test@gmail.com", "test");
        assertEquals("syntax", response);
    }

    @Test
    public void signUpEmailSyntax() {
        String response = ServerRequests.signUp("test", "testgmail.com", "test");
        assertEquals("syntax", response);
    }

    @Test
    public void signUpPasswordSyntax() {
        String response = ServerRequests.signUp("test", "test@gmail.com", "test!!!!");
        assertEquals("syntax", response);
    }

    @Test
    public void signUpComplete() {
        User newUser = new User("test100", "test100@gmail.com", "test100");
        String response = ServerRequests.signUp(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
        assertEquals("ok", response);
        userRepository.delete(newUser);
    }

    @Test
    public void signUpfailure() {
        String response = ServerRequests.signUp("test", "test@gmail.com", "test");
        assertEquals("fail", response);
    }

    @Test
    public void addFood() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addFood();
        assertEquals("1000",response);
    }

    @Test
    public void localFood() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.localFood();
        assertEquals("9000", response);
    }

    @Test
    public void addSolarPanel() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("5", "250", LocalDate.of(2019, 4,1).toString());
        assertEquals("[\"5\",\"1678\"]", response);
    }

    @Test
    public void addSolarPanelAmountNull() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel(null, "250", LocalDate.of(2019, 4,1).toString());
        assertNull(response);
    }

    @Test
    public void addSolarPanelPowerNull() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("100", null, LocalDate.now().toString());
        assertNull(response);
    }

    @Test
    public void addSolarPanelDateNull() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("300", "200", null);
        assertNull(response);
    }

    @Test
    public void addSolarPanelFuture() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("10", "50", LocalDate.of(2030, 5, 2).toString());
        assertEquals("[\"10\",\"671\"]", response);
    }

    @Test
    public void addSolarPanelDateAndAmountNull() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel(null, "101", null);
        assertNull(response);
    }

    @Test
    public void addSolarPanelDateAndPowerNull() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("100", null, null);
        assertNull(response);
    }

    @Test
    public void addSolarPanelNullAll() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel(null, null, null);
        assertNull(response);
    }

    @Test
    public void addSolarPanelWrongInput() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.addSolarPanel("number1", "number2", LocalDate.of(2019, 4,1).toString());
        assertEquals("fail", response);
    }

    @Test
    public void loweredTemp() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.loweredTemp();
        assertEquals("1500", response);
    }

    @Test
    public void displayEmission() {
        UserData userData = new UserData("junit", "5000", "0", "0", "0", "0", "0", "0", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayEmission();
        assertEquals("5", response);
    }

    @Test
    public void displaySolarPanel() {
        UserData userData = new UserData("junit", "0", "100", "0", "0", "0", "0", "0", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displaySolarPanels();
        assertEquals("100", response);
    }

    @Test
    public void displayVeganMeals() {
        UserData userData = new UserData("junit", "0", "0", "0", "0", "5", "0", "0", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayVeganMeals();
        assertEquals("5", response);
    }

    @Test
    public void displayKmTravelledByBike() {
        UserData userData = new UserData("junit", "0", "0", "0", "0", "0", "0", "100", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayKmTravelledByBike();
        assertEquals("100", response);
    }

    @Test
    public void displayKmTravelledByPubTransport() {
        UserData userData = new UserData("junit", "0", "0", "0", "0", "0", "0", "0", "1000", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayKmTravelledByPubTransport();
        assertEquals("1000", response);
    }

    @Test
    public void displayLocalProducts() {
        UserData userData = new UserData("junit", "0", "0", "0", "0", "0", "10", "0", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayLocalProducts();
        assertEquals("10", response);
    }

    @Test
    public void displayDailyEmission() {
        UserData userData = new UserData("junit", "0", "0", "3000", "0", "0", "0", "0", "0", "0");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.displayDailyEmission();
        assertEquals("3000", response);
    }

    @Test
    public void co2ReductionBike() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.co2ReductionBike("50", "10");
        assertEquals("[\"junit\",\"50\",\"10\"]",response);
    }

    @Test
    public void co2ReductionPT() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.co2ReductionPT("100", "50");
        assertEquals("[\"junit\",\"100\",\"50\"]",response);
    }

    @Test
    public void assertNotEqualsCo2ReductionPT() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.co2ReductionPT("60", "50");
        assertNotEquals("[\"junit\",\"60\",\"0\"]",response);
    }

    @Test
    public void getTheme() {
        UserData userData = new UserData("junit", "0", "0", "3000", "0", "0", "0", "0", "0", "pink");
        userDataRepository.save(userData);
        ServerRequests.setUsername("junit");
        String response = ServerRequests.getTheme();
        assertEquals("pink", response);
    }

    @Test
    public void setTheme() {
        ServerRequests.setUsername("junit");
        String response = ServerRequests.setTheme("blue");
        assertEquals("blue", response);
    }

    @Test
    public void sendRequestToServerNoJson() {
        assertNull(ServerRequests.sendRequestToServer("/", " no json "));
    }

    @Test
    public void sendRequestToServerEmptyJsonString() {
        assertNull(ServerRequests.sendRequestToServer("/", ""));
    }
}
