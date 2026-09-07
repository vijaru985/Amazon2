package testRunner;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import utility.BaseClass;
import utility.ExcelReader;
import utility.TestCaseData;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepDefinitions",
                "hooks"
        },
        monochrome = true,
        tags = "",
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-report.html"
        }
)
public class TestRunner
        extends AbstractTestNGCucumberTests {

    private final TestCaseData testCaseData;

    public TestRunner(TestCaseData testCaseData) {
        this.testCaseData = testCaseData;
    }
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        Object[][] allScenarios =
                super.scenarios();

        List<Object[]> matchingScenarios =
                new ArrayList<>();

        for (Object[] scenario : allScenarios) {

            PickleWrapper pickleWrapper =
                    (PickleWrapper) scenario[0];

            String scenarioName =
                    pickleWrapper
                            .getPickle()
                            .getName();

            String featurePath =
                    pickleWrapper
                            .getPickle()
                            .getUri()
                            .getPath();

            if (isMatchingScenario(
                    featurePath,
                    scenarioName)) {

                matchingScenarios.add(scenario);
            }
        }

        return matchingScenarios.toArray(
                new Object[0][]);
    }

    @Factory
    @Parameters({"dataFile", "dataSheet"})
    public static Object[] createTestInstances(
            String dataFile,
            String dataSheet) {

        Object[][] testData =
                ExcelReader.getTestData(dataFile, dataSheet);

        Object[] runners =
                new Object[testData.length];

        for (int i = 0; i < testData.length; i++) {

            TestCaseData data =
                    (TestCaseData) testData[i][0];

            runners[i] =
                    new TestRunner(data);
        }

        return runners;
    }
    
    private boolean isMatchingScenario(
            String featurePath,
            String scenarioName) {

        String expectedFeature =
                testCaseData
                        .getFeature()
                        .replace("\\", "/")
                        .trim();

        String expectedScenario =
                testCaseData
                        .getScenario()
                        .trim();

        String normalizedFeaturePath =
                featurePath
                        .replace("\\", "/");

        boolean featureMatches =
                normalizedFeaturePath
                        .endsWith("/" + expectedFeature);

        boolean scenarioMatches =
                scenarioName
                        .trim()
                        .equalsIgnoreCase(
                                expectedScenario);

        return featureMatches
                && scenarioMatches;
    }

    @Override
    public String toString() {

        return "TestRunner - "
                + testCaseData.getTestCaseId();
    }
    
    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {

        try {
            BaseClass.setTestData(testCaseData);

            super.runScenario(pickleWrapper, featureWrapper);

        } finally {
            BaseClass.unloadTestData();
        }
    }
}