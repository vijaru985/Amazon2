package utility;

import java.util.HashMap;

public class ScenarioContext {

    private static final ThreadLocal<HashMap<String, String>>
            dataTable =
            ThreadLocal.withInitial(HashMap::new);

    public static HashMap<String, String> getDataTable() {

        return dataTable.get();
    }

    public static void setDataTable(
            HashMap<String, String> data) {

        dataTable.set(data);
    }

    public static void unload() {

        dataTable.remove();
    }
}