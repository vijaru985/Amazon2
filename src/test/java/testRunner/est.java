package testRunner;

import org.testng.annotations.Parameters;

public class est {

	static {
		System.out.println("static");
	}
	
	@org.testng.annotations.Test
	@Parameters("browser")
	public static void test(String s) {
		System.out.println(s);
	}
	
	//@org.testng.annotations.Test
	public static void main1(String s) {
		System.out.println("Hiiii");
		test("Hi");
	}
}
