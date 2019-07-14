package project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(length = 50, updatable = false, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50, updatable = false, nullable = false)
    private String password;

    public User() {

    }

    /**
     * This is the constructor of the class.
     * @param username is username
     * @param email is email
     * @param password is password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Method returns the username.
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the username.
     * @param username is username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method returns the email of the user.
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the email of the user.
     * @param email is email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    /**
     * This method sets the password of the user.
     * @param password is password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
