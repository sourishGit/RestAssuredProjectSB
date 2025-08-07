package JiraDemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://sourishcse.atlassian.net";
		String createIssueResponse = given()		
		.header("Content-Type","application/json")
		.header("Authorization","Basic c291cmlzaC5jc2VAZ21haWwuY29tOkFUQVRUM3hGZkdGMFpSaE8tcFpxdW1pdkdkX2NkdHdYZFFIT3FXVjd0MXBOWXA2NC15VUZTRkdrcjA1TkpJU3l3MFMzT3FnUFRXTk9kRnB3MU13U3dnQjBBS0JKZVctTW82c2F1RGFQZzdPUlNzVkh4Ri11bGtMYVJRUkRocHhNYVhJbGFSQjJRM29DYUlCcTFKN0JWbHhFQlhENDJZUEw1Uk1PUHNkU1dBd3dEUkdxekhXYmlUcz02NjdDMUFFQQ==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"SCRUM\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"PSF Clears2 | sys | Items not loading - RestAssured Automation\",\r\n"
				+ "        \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}")
		.log().all()
		.post("rest/api/2/issue/").then().log().all().assertThat().statusCode(201)
		
		.extract().response().asString();
		
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueid = js.get("id");
		System.out.println(issueid);
		
		given()
		.pathParam("key", issueid)
		.header("X-Atlassian-Token","nocheck")
		.header("Authorization","Basic c291cmlzaC5jc2VAZ21haWwuY29tOkFUQVRUM3hGZkdGMFpSaE8tcFpxdW1pdkdkX2NkdHdYZFFIT3FXVjd0MXBOWXA2NC15VUZTRkdrcjA1TkpJU3l3MFMzT3FnUFRXTk9kRnB3MU13U3dnQjBBS0JKZVctTW82c2F1RGFQZzdPUlNzVkh4Ri11bGtMYVJRUkRocHhNYVhJbGFSQjJRM29DYUlCcTFKN0JWbHhFQlhENDJZUEw1Uk1PUHNkU1dBd3dEUkdxekhXYmlUcz02NjdDMUFFQQ==")
		.multiPart("file", new File("C:\\Users\\SOURISH\\Pictures\\Capture1.jpg")).log().all()
		.post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);

	}

}
