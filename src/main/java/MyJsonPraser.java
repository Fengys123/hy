import com.dlut.entity;
import com.google.gson.*;
import org.junit.Test;

import	java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import	java.io.FileReader;
import java.util.Map;

public class MyJsonPraser {
    private static final String path = "C:\\Users\\fys\\Desktop\\meal-db\\";

    public List<String> areasJsonToList() throws IOException {
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

    public Map<String ,entity> mealsJsonToMap() throws IOException {
        Map<String, entity> resultMap = new HashMap<>(30);
        String strJsonFromFile = getStrJsonFromFile(path + "meals.json");
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();
        JsonObject jsonObject = jsonParser.parse(strJsonFromFile).getAsJsonObject();
        for (String s : jsonObject.keySet()) {
            JsonElement jsonElement = jsonObject.get(s);
            String asString = gson.toJson(jsonElement);
            System.out.println(asString);
            entity entity = gson.fromJson(asString, entity.class);
            resultMap.put(s, entity);
        }
        return resultMap;
    }


    /**
     * {
     *     "name" : ,"meals",
     *     "children" : [
     *          "name" : "china"
     *          "children" : [
     *              "name" : "kauican"
     *              "children" : [
     *                  "name" : "shutiao"
     *                  "children" : [
     *                      "name" : "tudou"
     *                  ]
     *              ]
     *          ]
     *     ]
     * }
     */
    public JsonObject convertToNeedFormat()
    {
        return null;
    }



    private String getStrJsonFromFile(String path) throws IOException {
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

    @Test
    public void test() throws IOException {
        areasJsonToList();
    }

    @Test
    public void jsonToString() {
        JsonArray ja = new JsonArray();
        for (int i = 0; i < 10; i++) {
            ja.add(i);
        }
        System.out.println(ja.toString());
    }

    @Test
    public void stringToJson() {
        String s = "[0,1,2,3,4,5,6,7,8,9]";
        JsonArray asJsonArray = new JsonParser().parse(s).getAsJsonArray();
    }

    @Test
    public void testmealsJsonToMap() throws IOException {
        mealsJsonToMap();
    }
}
