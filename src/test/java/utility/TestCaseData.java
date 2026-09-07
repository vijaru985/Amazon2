package utility;

import java.util.Map;

public class TestCaseData {

    private final Map<String, String> data;

    public TestCaseData(Map<String, String> data) {
        this.data = data;
    }

    public String get(String columnName) {
        return data.get(columnName);
    }

    public String getTestCaseId() {
        return get("TestCaseID");
    }

    public String getFeature() {
        return get("Feature");
    }

    public String getScenario() {
        return get("Scenario");
    }

    @Override
    public String toString() {
        return data.toString();
    }
}