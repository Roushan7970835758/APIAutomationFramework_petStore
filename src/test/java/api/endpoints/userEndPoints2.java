package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//created to do CRUD oprations
public class userEndPoints2 {

	//method createdfor getting urls  from properties file
	static ResourceBundle getURL()
	{
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load properties file return routes;
		return routes;
	}

	
	public static Response createUser(User payload) {
		
		String post_url  = getURL().getString("post_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when()
				.post(Routes.post_url);
		return res;
	}

	public static Response getUser(String username) {
		Response res = given().pathParam("username", username).when().get(Routes.get_url);
		return res;
	}

	public static Response readUser(String userName)
	{
		String get_url  = getURL().getString("get_url");

		Response res = given()

				.pathParam("username", userName)

				.when()

				.get(get_url);

		return res;

	}

	public static Response updateUser(String username, User payload) {
		
		String update_url  = getURL().getString("update_url");
		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("username", username)
				.body(payload).when().put(update_url);
		return res;
	}

	public static Response deleteUser(String username) {
		String delete_url  = getURL().getString("delete_url");
		
		Response res = given().pathParam("username", username).when().delete(delete_url);

		return res;
	}

}
