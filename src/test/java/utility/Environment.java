package utility;

public class Environment {

	public static String getBrowser() {
		
		//1. check if browser is set in system property
		String browser = System.getProperty("driver");
		
		//2. if not set in system property, check if browser is set in environment variable
		if(browser == null || browser.isBlank()) {
			browser = System.getenv("driver");
		}
		
		
		return browser;
	}
	
	public static String getUsername() {

	    String username = System.getProperty("username");

	    if (username == null || username.isBlank()) {
	        username = System.getenv("AMAZON_USERNAME");
	    }

	    if (username == null || username.isBlank()) {
	        throw new RuntimeException(
	                "Username is not available from system property or environment variable");
	    }

	    return username;
	}
	
	public static String getPassword() {

		String password = System.getProperty("password");

		if (password == null || password.isBlank()) {
			password = System.getenv("AMAZON_PASSWORD");
		}

		return password;
	}
	
	public static String getDBUsername() {

		String username = System.getProperty("DB_USERNAME");

		if (username == null || username.isBlank()) {
			username = System.getenv("DB_USERNAME");
		}

		return username;
	}
	
	public static String getDBPassword() {

		String password = System.getProperty("DB_PASSWORD");

		if (password == null || password.isBlank()) {
			password = System.getenv("DB_PASSWORD");
		}

		return password;
	}
	
}
