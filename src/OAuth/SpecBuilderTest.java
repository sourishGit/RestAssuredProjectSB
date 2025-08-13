package OAuth;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import Pojo.AddPlace;
import Pojo.Location;

public class SpecBuilderTest {
	

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
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().log().all().spec(req).body(p);
		
		Response response = res.when().post("/maps/api/place/add/json")
		.then().spec(resSpec).extract().response();
		
		String responseString = response.asString();
		System.out.println("Response now " + responseString);
	}

}
