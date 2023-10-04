package club.dongfang7su.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GetJsonData {

    private final ArrayList<String> indexList = new ArrayList<>();
    private final ArrayList<String> indexTitleList = new ArrayList<>();

    private String videoName = null;

    public GetJsonData(File[] dirPathList) {
        try {
            JsonObject jsonObject = null;
            for (File file : dirPathList) {
                JsonReader reader = Json.createReader(new FileReader(file.getPath() + "\\entry.json"));
                jsonObject = reader.readObject();
                this.indexList.add(jsonObject.getJsonObject("ep").getString("index"));
                this.indexTitleList.add(jsonObject.getJsonObject("ep").getString("index_title"));
            }
            if (jsonObject != null) {
                this.videoName = jsonObject.getString("title").replace(" ", "_");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getIndexList() {
        return this.indexList;
    }

    public ArrayList<String> getIndexTitleList() {
        return this.indexTitleList;
    }

    public String getVideoName() {
        return this.videoName;
    }
}
