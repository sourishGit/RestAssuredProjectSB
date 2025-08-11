package OAuth;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.WebAutomation;

public class oAuthTestPojo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String expectedTitles[] = {"Selenium Webdriver Java","Cypress","Protractor"};
		String response = given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type","client_credentials")
		.formParams("scope","trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.get("access_token");
		
		System.out.println("Access token : "+accessToken);
		
		GetCourse gc = given().log().all()
				.queryParams("access_token",accessToken)
				.when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		List<Api> listofCourses = gc.getCourses().getApi();
		
		for(int i=0;i<listofCourses.size();i++)
		{
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println("API Course Title : "+gc.getCourses().getApi().get(i).getCourseTitle());
				System.out.println("API Course price : "+gc.getCourses().getApi().get(i).getPrice());
			}
		}
		
		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> listofWebAutCourses = gc.getCourses().getWebAutomation();
		
		for(int i=0;i<listofWebAutCourses.size();i++)
		{
			
				System.out.println("Web Automation Course Title : "+gc.getCourses().getWebAutomation().get(i).getCourseTitle());
				a.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
				
			
		}
		
		List<String> expectedList = Arrays.asList(expectedTitles);
		Assert.assertTrue(a.equals(expectedList));
	}

}
