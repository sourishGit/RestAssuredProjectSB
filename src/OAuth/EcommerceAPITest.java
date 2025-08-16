package OAuth;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.OrderDetails;
import Pojo.Orders;

public class EcommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest lr = new LoginRequest();
		lr.setUserEmail("alb@gmail.com");
		lr.setUserPassword("Abc@1234");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(lr);
		ResponseSpecification resSpecLogin = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		LoginResponse resLogin = reqLogin.when().post("/api/ecom/auth/login")
		.then().log().all().spec(resSpecLogin).extract().response().as(LoginResponse.class);
		
		String token = resLogin.getToken();
		System.out.println(resLogin.getUserId());
		String userID = resLogin.getUserId();
		
		//Add Product
		RequestSpecification reqbaseAddProd = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		RequestSpecification reqAddProd = given().log().all().spec(reqbaseAddProd).param("productName", "Laptop")
		.param("productAddedBy", userID)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice","11500")
		.param("productDescription","Addias Originals")
		.param("productFor", "women")
		.multiPart("productImage", new File("C:\\Users\\SOURISH\\Postman\\files\\Capture1.jpg"));
		
		String responseAddProd = reqAddProd.when().post("api/ecom/product/add-product")
		.then().log().all().extract().asString();
		
		JsonPath js = new JsonPath(responseAddProd);
		String productId = js.get("productId");
		System.out.println(productId);
		
		//Create order
		
		
		RequestSpecification reqbaseCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		OrderDetails od = new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(od);
		
		Orders os = new Orders();
		os.setOrders(orderDetailList);
		
		RequestSpecification reqOrderReq = given().log().all().spec(reqbaseCreateOrder).body(os);
		String responseAddOrder = reqOrderReq.when().post("api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		
		//Delete Product
		
		RequestSpecification reqbaseDeleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification reqDeleteProduct = given().relaxedHTTPSValidation().log().all().spec(reqbaseDeleteProduct).pathParam("productId", productId);
		
		String deleteProductResponse = reqDeleteProduct.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
				

	}

}
