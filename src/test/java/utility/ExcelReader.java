package utility;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class ExcelReader {

    private static final String FILE_PATH =
            "testdata/TestData.xlsx";

    private static final String SHEET_NAME =
            "TestCases";

    public static Object[][] getTestData() {

        try (InputStream inputStream =
                     ExcelReader.class
                             .getClassLoader()
                             .getResourceAsStream(FILE_PATH)) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "Excel file not found: " + FILE_PATH);
            }

            try (Workbook workbook =
                         WorkbookFactory.create(inputStream)) {

                Sheet sheet =
                        workbook.getSheet(SHEET_NAME);

                if (sheet == null) {
                    throw new RuntimeException(
                            "Excel sheet not found: "
                                    + SHEET_NAME);
                }

                DataFormatter formatter =
                        new DataFormatter();

                // Read header row
                Row headerRow = sheet.getRow(0);

                int lastColumn =
                        headerRow.getLastCellNum();

                String[] headers =
                        new String[lastColumn];

                for (int column = 0;
                     column < lastColumn;
                     column++) {

                    headers[column] =
                            formatter
                                    .formatCellValue(
                                            headerRow
                                                    .getCell(column))
                                    .trim();
                }

                Object[][] tempData =
                        new Object[sheet.getLastRowNum()][1];

                int dataIndex = 0;

                // Read data rows
                for (int rowIndex = 1;
                     rowIndex <= sheet.getLastRowNum();
                     rowIndex++) {

                    Row row =
                            sheet.getRow(rowIndex);

                    if (row == null) {
                        continue;
                    }

                    Map<String, String> rowData =
                            new LinkedHashMap<>();

                    for (int column = 0;
                         column < lastColumn;
                         column++) {

                        String header =
                                headers[column];

                        String value =
                                formatter.formatCellValue(
                                        row.getCell(column))
                                        .trim();

                        rowData.put(header, value);
                    }

                    String runmode =
                            rowData.get("Runmode");

                    if ("Yes".equalsIgnoreCase(runmode)) {

                        TestCaseData testCase =
                                new TestCaseData(rowData);

                        tempData[dataIndex][0] =
                                testCase;

                        dataIndex++;
                    }
                }

                Object[][] executableData =
                        new Object[dataIndex][1];

                System.arraycopy(
                        tempData,
                        0,
                        executableData,
                        0,
                        dataIndex);

                return executableData;
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read test data from Excel",
                    e);
        }
    }
}