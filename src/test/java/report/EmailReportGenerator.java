package report;

import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailReportGenerator {

    public static void generateReport() throws Exception {

        String json = Files.readString(Paths.get("target/cucumber.json"));

        JsonArray features = JsonParser.parseString(json).getAsJsonArray();

        StringBuilder rows = new StringBuilder();

        int total = 0;
        int passed = 0;
        int failed = 0;

        for (JsonElement featureElement : features) {

            JsonObject feature = featureElement.getAsJsonObject();

            JsonArray scenarios = feature.getAsJsonArray("elements");

            for (JsonElement scenarioElement : scenarios) {

                total++;

                JsonObject scenario = scenarioElement.getAsJsonObject();

                String scenarioName = scenario.get("name").getAsString();

                JsonArray steps = scenario.getAsJsonArray("steps");

                StringBuilder methods = new StringBuilder();

                boolean scenarioFailed = false;

                for (JsonElement stepElement : steps) {

                    JsonObject step = stepElement.getAsJsonObject();

                    String status =
                            step.getAsJsonObject("result")
                                    .get("status")
                                    .getAsString();

                    if (!status.equalsIgnoreCase("skipped")) {

                        String location =
                                step.getAsJsonObject("match")
                                        .get("location")
                                        .getAsString();

                        String method =
                                location.substring(location.lastIndexOf(".") + 1);

                        method = method.replaceAll("\\(.*\\)", "()");

                        methods.append(method).append("<br>");

                    }

                    if (status.equalsIgnoreCase("failed")) {

                        scenarioFailed = true;

                    }

                }

                if (scenarioFailed)
                    failed++;
                else
                    passed++;

                rows.append("<tr>")
                        .append("<td>").append(scenarioName).append("</td>")
                        .append("<td>").append(methods).append("</td>")
                        .append("<td style='color:")
                        .append(scenarioFailed ? "red" : "green")
                        .append("'><b>")
                        .append(scenarioFailed ? "Failed" : "Passed")
                        .append("</b></td>")
                        .append("</tr>");

            }

        }

        String html = """
<html>
<head>

<style>

body{
font-family:Calibri;
}

table{
border-collapse:collapse;
width:100%;
}

th{
background:#003366;
color:white;
padding:10px;
}

td{
border:1px solid #cccccc;
padding:8px;
vertical-align:top;
}

</style>

</head>

<body>

<h2>Automation Test Execution Report</h2>

<table style='width:300px'>

<tr><td>Total</td><td>%d</td></tr>

<tr><td style='color:green'>Passed</td><td>%d</td></tr>

<tr><td style='color:red'>Failed</td><td>%d</td></tr>

</table>

<br>

<table>

<tr>

<th>Scenario</th>

<th>Executed Test Methods</th>

<th>Status</th>

</tr>

%s

</table>

</body>

</html>
""".formatted(total, passed, failed, rows.toString());

        Files.writeString(Paths.get("target/emailReport.html"), html);

    }

}