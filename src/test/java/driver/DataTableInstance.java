package driver;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

public class DataTableInstance {

private static HashMap<String, String> dataTable = new HashMap<>();
	
	public static HashMap<String, String> getDataTable() {
		return dataTable;
	}
	
	
	public static void setDataTable(HashMap<String, String> dataTable) {
		DataTableInstance.dataTable = dataTable;
	}
	
}
