package project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserData")
public class UserData {

    @Id
    @Column(length = 50, updatable = false, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String co2Reduction;

    @Column(length = 50, nullable = false)
    private String solarPanels;

    @Column(length = 50, nullable = false)
    private String dailyCo2reduction;

    @Column(length = 50, nullable = false)
    private String loweredTemp;

    @Column(length = 50, nullable = false)
    private String veganMeals;

    @Column(length = 50, nullable = false)
    private String localFood;

    @Column(length = 50, nullable = false)
    private String kmByBike;

    @Column(length = 50, nullable = false)
    private String kmByPublicTransport;

    @Column(length = 50, nullable = false)
    private String theme;

    public UserData() {}

    /**
     * This is the constructor of the class.
     * @param username is username
     * @param co2Reduction is co2 reduction
     * @param solarPanels is amount of installed solar panels
     * @param dailyCo2reduction is daily co2 reduction
     * @param loweredTemp is days of lowered temperature
     * @param veganMeals is amount of eaten vegan meals
     * @param localFood is amount of local food purchased
     * @param kmByBike is km ridden by bike
     * @param kmByPublicTransport is km ridden with public transport
     * @param theme this is user theme
     */
    public UserData(String username, String co2Reduction, String solarPanels,
                    String dailyCo2reduction, String loweredTemp, String veganMeals,
                    String localFood, String kmByBike, String kmByPublicTransport, String theme) {
        this.username = username;
        this.co2Reduction = co2Reduction;
        this.solarPanels = solarPanels;
        this.dailyCo2reduction = dailyCo2reduction;
        this.loweredTemp = loweredTemp;
        this.veganMeals = veganMeals;
        this.localFood = localFood;
        this.kmByBike = kmByBike;
        this.kmByPublicTransport = kmByPublicTransport;
        this.theme = theme;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCo2Reduction() {
        return co2Reduction;
    }

    public void setCo2Reduction(String co2Reduction) {
        this.co2Reduction = co2Reduction;
    }

    public String getSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(String solarPanels) {
        this.solarPanels = solarPanels;
    }

    public String getDailyCo2reduction() {
        return dailyCo2reduction;
    }

    public void setDailyCo2reduction(String dailyCo2reduction) {
        this.dailyCo2reduction = dailyCo2reduction;
    }

    public String getLoweredTemp() {
        return loweredTemp;
    }

    public void setLoweredTemp(String loweredTemp) {
        this.loweredTemp = loweredTemp;
    }

    public String getVeganMeals() {
        return veganMeals;
    }

    public void setVeganMeals(String veganMeals) {
        this.veganMeals = veganMeals;
    }

    public String getLocalFood() {
        return localFood;
    }

    public void setLocalFood(String localFood) {
        this.localFood = localFood;
    }

    public String getKmByBike() {
        return kmByBike;
    }

    public void setKmByBike(String kmByBike) {
        this.kmByBike = kmByBike;
    }

    public String getKmByPublicTransport() {
        return kmByPublicTransport;
    }

    public void setKmByPublicTransport(String kmByPublicTransport) {
        this.kmByPublicTransport = kmByPublicTransport;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
