package project.bean;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    public final User test = new User("verjak", "verjak@gmail.com" , "123");
    public final User test2= new User();

    @Test
    public void getUsername() {
        assertEquals("verjak", test.getUsername());
    }

    @Test
    public void setUsername() {
        test2.setUsername("agrayel");
        assertEquals("agrayel", test2.getUsername());
    }

    @Test
    public void getEmail() {
        assertEquals("verjak@gmail.com", test.getEmail());
    }

    @Test
    public void setEmail() {
        test2.setEmail("agrayel@gmail.com");
        assertEquals("agrayel@gmail.com", test2.getEmail());
    }

    @Test
    public void getPassword() {
        assertEquals("123", test.getPassword());
    }

    @Test
    public void setPassword() {
        test2.setPassword("1234");
        assertEquals("1234", test2.getPassword());
    }
}