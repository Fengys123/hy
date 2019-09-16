import com.dlut.entity;
import com.google.gson.*;
import org.junit.Test;

import java.util.*;
import java.io.File;
import java.io.IOException;

import	java.io.FileReader;

public class MyJsonPraser {
    private static final String path = "C:\\Users\\fys\\Desktop\\meal-db\\";

    private static final Map<String ,entity> mealsmap = mealsJsonToMap();

    private List<String> areasJsonToList() throws IOException {
        List<String> resultList = new ArrayList<>();
        String strJsonFromFile = getStrJsonFromFile(path + "areas.json");
        JsonParser jsonParser = new JsonParser();
        JsonArray ja = jsonParser.parse(strJsonFromFile).getAsJsonArray();
        if(ja == null) {
            throw new NullPointerException();
        }
        for (JsonElement jsonElement : ja) {
            resultList.add(jsonElement.getAsString());
        }
        return resultList;
    }

    private static Map<String ,entity> mealsJsonToMap() {
        Map<String, entity> resultMap = new HashMap<>(30);
        String strJsonFromFile = null;
        try {
            strJsonFromFile = getStrJsonFromFile(path + "meals.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();
        JsonObject jsonObject = jsonParser.parse(strJsonFromFile).getAsJsonObject();
        for (String s : jsonObject.keySet()) {
            JsonElement jsonElement = jsonObject.get(s);
            String asString = gson.toJson(jsonElement);
            entity entity = gson.fromJson(asString, entity.class);
            resultMap.put(s, entity);
        }
        return resultMap;
    }

    @Test
    public void test() {
        JsonObject Jo = new JsonObject();
        Jo.addProperty("name","meal");
        JsonArray jsonArray = new JsonArray();
        buildTree(jsonArray);
        Jo.add("children",jsonArray);
        System.out.println(Jo.toString());
    }

    public void buildTree(JsonArray ja){
        Set<String> strings = mealsmap.keySet();
        for (String string : strings) {
            entity entity = mealsmap.get(string);
            String area = entity.getArea();
            String category = entity.getCategory();
            String name = entity.getName();
            List<String> ingredients = entity.getIngredients();
            JsonArray ja1 = doJudgeContains(ja,area);
            if (ja1 == null) {
                ja1 = new JsonArray();
                JsonObject jo1 = new JsonObject();
                jo1.addProperty("name", area);
                jo1.add("children", ja1);
                ja.add(jo1);
            }

            JsonArray ja2 = doJudgeContains(ja1,category);
            if (ja2 == null) {
                ja2 = new JsonArray();
                JsonObject jo1 = new JsonObject();
                jo1.addProperty("name", category);
                jo1.add("children", ja2);
                ja1.add(jo1);
            }

            JsonArray ja3 = doJudgeContains(ja2,name);
            if (ja3 == null) {
                ja3 = new JsonArray();
                JsonObject jo1 = new JsonObject();
                jo1.addProperty("name", name);
                jo1.add("children", ja3);
                ja2.add(jo1);
            }

            JsonArray ja4 = doJudgeContains(ja3,ingredients.get(0));
            if (ja4 == null) {
                for (String ingredient : ingredients) {
                    JsonObject jo1 = new JsonObject();
                    jo1.addProperty("name", ingredient);
                    ja3.add(jo1);
                }
            }

        }

    }

    private JsonArray doJudgeContains(JsonArray ja, String strValue)
    {
        if(ja == null)
        {
            return null;
        }
        for (JsonElement jsonElement : ja) {
            JsonElement name = jsonElement.getAsJsonObject().get("name");
            if(name != null && name.getAsString().equals(strValue))
            {
                return (JsonArray)jsonElement.getAsJsonObject().get("children");
            }
        }
        return null;
    }

    private static String getStrJsonFromFile(String path) throws IOException {
        FileReader fileReader = new FileReader(new File(path));
        char cbuf[] = new char[1024];
        int i;
        String strJson = "";
        while((i = fileReader.read(cbuf)) != -1){
            for (int j=0 ; j<i ; j++)
            {
                strJson = strJson + cbuf[j];
            }
        }
        return strJson;
    }
}
