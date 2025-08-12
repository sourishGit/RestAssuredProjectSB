package OAuth;

import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import Pojo.AddPlace;
import Pojo.Location;

public class SerializeTest {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddPlace p = new AddPlace();
		Location lc = new Location();
		p.setAccuracy(50);
		p.setAddress("Colooo. Cohen NY");
		p.setLanguage("Bengali");
		p.setPhone_number("+91 987 987 987 675");
		p.setWebsite("www.google.com");
		p.setName("Sam");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		lc.setLat(-38.383494);
		lc.setLng(33.427362);
		p.setLocation(lc);
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		Response response = given().log().all().queryParam("key", "qaclick123").body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println("Response now " + responseString);
	}

}
