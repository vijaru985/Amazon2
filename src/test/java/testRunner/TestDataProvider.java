package testRunner;

import org.testng.annotations.DataProvider;

import utility.ExcelReader;

public class TestDataProvider {

    @DataProvider(
            name = "testScenarios",
            parallel = false)
    public static Object[][] testScenarios() {

        return ExcelReader.getTestData();
    }
}