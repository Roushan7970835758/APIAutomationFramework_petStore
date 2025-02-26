package api.test;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import api.endpoints.userEndPoints2;
import api.payload.User;
import freemarker.log.Logger;
import io.restassured.response.Response;

public class UserTest2 {
	Faker faker;
	User userPayload;
	public org.apache.logging.log4j.Logger logger;

	@BeforeClass
	public void setupData() {
		faker = new Faker(); // faker is used to generate fake data in test id
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
//		userPayload.setUserStatus(0);
		
		//log
		logger = LogManager.getLogger(this.getClass());
		
		logger.debug(" Debugging........... ");
		
		

	}

	@Test(priority = 1)
	public void TestPostUser() {
		
		logger.info("******************** Creating user ************************");
		Response response = userEndPoints2.createUser(userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************************ User Created ***********************");

	}

	@Test(priority = 2)
	public void TestGetUserByName() {
		
		logger.info("******************** Reading user info ************************");
		Response response = userEndPoints2.readUser(this.userPayload.getUsername());

		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******************** User info displayed ************************");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		logger.info("******************** Updating User ************************");

		// update data using payload

		userPayload.setFirstName(faker.name().firstName());

		userPayload.setLastName(faker.name().lastName());

		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = userEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******************** User is Updated ************************");

		// checking data after update
		Response response2 = userEndPoints2.readUser(this.userPayload.getUsername());

		Assert.assertEquals(response2.getStatusCode(), 200);
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		logger.info("******************** Deleting user ************************");
		Response res = userEndPoints2.deleteUser(this.userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info("******************** user Deleted ************************");
	}

}