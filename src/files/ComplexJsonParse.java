package files;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.coursePrice());
		
		//Print No of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print All course titles and their respective Prices
		
		for(int i=0;i<count;i++)
		{
			String courseTitles = js.get("courses[" + i + "].title");
			System.out.println(courseTitles);
			System.out.println(js.get("courses[" + i + "].price").toString());
		}
		
		System.out.println("Print no of copies sold by RPA Course");
		
		for(int i=0;i<count;i++)
		{
			String courseTitles = js.get("courses[" + i + "].title");
			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				System.out.println(js.get("courses[" + i + "].copies").toString());
				break;
			}
			
		}
		
		System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");
		

	}

}
