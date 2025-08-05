package files;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses()
	{
		JsonPath js = new JsonPath(payload.coursePrice());
		int count = js.getInt("courses.size()");
		int sum = 0;
		System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");
		
		for(int i=0;i<count;i++)
		{
			int price = js.get("courses[" + i + "].price");
			int copies = js.get("courses[" + i + "].copies");
			int amount = price * copies;
			System.out.println(amount);
			sum=sum+amount;
		}
		
		System.out.println("Total amount : " + sum );
		int purchaseAmt = js.get("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmt);
		
	}
	

}
