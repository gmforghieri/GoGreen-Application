package project.bean;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserDataTest {

    public final UserData test = new UserData("verjak", "0" , "1", "2", "3", "4", "5", "6", "7", "pink");
    public final UserData test2= new UserData();

    @Test
    public void getUsername() {assertEquals("verjak", test.getUsername());}

    @Test
    public void setUsername() {
        test2.setUsername("junit");
        assertEquals("junit", test2.getUsername());
    }

    @Test
    public void getco2Reduction() {
        assertEquals("0", test.getCo2Reduction());
    }

    @Test
    public void setco2Reduction() {
        test2.setCo2Reduction("2");
        assertEquals("2", test2.getCo2Reduction());
    }

    @Test
    public void getSolarPanels() {assertEquals("1", test.getSolarPanels());}

    @Test
    public void setSolarPanels() {
        test2.setSolarPanels("3");
        assertEquals("3", test2.getSolarPanels());
    }

    @Test
    public void getDailyCo2Reduction() { assertEquals("2", test.getDailyCo2reduction()); }

    @Test
    public void setDailyCo2Reduction() {
        test2.setDailyCo2reduction("4");
        assertEquals("4", test2.getDailyCo2reduction());
    }

    @Test
    public void getLoweredTemp() { assertEquals("3", test.getLoweredTemp());}

    @Test
    public void setLoweredTemp() {
        test2.setLoweredTemp("5");
        assertEquals("5", test2.getLoweredTemp());
    }

    @Test
    public void getVeganMeals() {assertEquals("4", test.getVeganMeals());}

    @Test
    public void setVeganMeals() {
        test2.setVeganMeals("6");
        assertEquals("6", test2.getVeganMeals());
    }

    @Test
    public void getLocalFood() {assertEquals("5", test.getLocalFood());}

    @Test
    public void setLocalFood() {
        test2.setLocalFood("7");
        assertEquals("7", test2.getLocalFood());
    }

    @Test
    public void getKmByBike() {assertEquals("6", test.getKmByBike());}

    @Test
    public void setKmByBike() {
        test2.setKmByBike("22");
        assertEquals("22", test2.getKmByBike());
    }

    @Test
    public void getKmByPublicTransport() {assertEquals("7", test.getKmByPublicTransport());}

    @Test
    public void setKmByPublicTransport() {
        test2.setKmByPublicTransport("50");
        assertEquals("50", test2.getKmByPublicTransport());
    }

    @Test
    public void getTheme() {assertEquals("pink", test.getTheme());}

    @Test
    public void setTheme() {
        test2.setTheme("blue");
        assertEquals("blue", test2.getTheme());
    }
}
