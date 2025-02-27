package api.test;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints2;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UserTest2 {
    Faker faker;
    public org.apache.logging.log4j.Logger logger;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        logger = LogManager.getLogger(this.getClass());
        logger.debug("Debugging...........");
    }

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph) {
        logger.info("******************** Creating user ************************");
        
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(useremail);
        userPayload.setPassword(pwd);
        userPayload.setPhone(ph);
        
        Response response = userEndPoints2.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        
        logger.info("******************** User Created ************************");
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testGetUserByName(String userName) {
        logger.info("******************** Reading user info ************************");
        
        Response response = userEndPoints2.readUser(userName);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        
        logger.info("******************** User info displayed ************************");
    }

    @Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testUpdateUserByName(String userName) {
        logger.info("******************** Updating User ************************");
        
        // Retrieve existing user data
        Response getResponse = userEndPoints2.readUser(userName);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        User userData = getResponse.as(User.class);
        
        // Update fields using Faker
        userData.setFirstName(faker.name().firstName());
        userData.setLastName(faker.name().lastName());
        userData.setEmail(faker.internet().safeEmailAddress());
        
        Response updateResponse = userEndPoints2.updateUser(userName, userData);
        updateResponse.then().log().all();
        Assert.assertEquals(updateResponse.getStatusCode(), 200);
        
        logger.info("******************** User Updated ************************");
    }

    @Test(priority = 4, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String userName) {
        logger.info("******************** Deleting user ************************");
        
        Response response = userEndPoints2.deleteUser(userName);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        
        logger.info("******************** User Deleted ************************");
    }
}