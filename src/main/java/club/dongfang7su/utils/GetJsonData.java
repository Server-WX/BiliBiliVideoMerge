package club.dongfang7su.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GetJsonData {

    private final ArrayList<String> indexList = new ArrayList<>();
    private final ArrayList<String> indexTitleList = new ArrayList<>();
    private final ArrayList<File> availableFileList = new ArrayList<>();

    private String videoName = null;


    private static JsonObject getReader(File file) throws FileNotFoundException {
        JsonObject jsonObject;
        JsonReader reader = Json.createReader(new FileReader(file.getPath() + "\\entry.json"));
        jsonObject = reader.readObject();
        return jsonObject;
    }

    public GetJsonData(File[] dirPathList) {

        Scanner scanner = new Scanner(System.in);
        String cartoon = "ep";
        String userVideo = "page_data";

        for (File file : dirPathList) {
            try {
                if (!new File(file.getPath() + "\\entry.json").exists())
                    System.out.print(file.getPath());

                JsonObject jsonObject = getReader(file);

                this.videoName = getReader(file).getString("title").replace(" ", "_");
                if (jsonObject.getJsonObject(cartoon) != null) {
                    this.indexList.add(jsonObject.getJsonObject(cartoon).getString("index"));
                    this.indexTitleList.add(jsonObject.getJsonObject(cartoon).getString("index_title"));
                } else if (jsonObject.getJsonObject(userVideo) != null) {
                    this.indexList.add(jsonObject.getJsonObject(userVideo).getString("download_subtitle"));
                    this.indexTitleList.add(jsonObject.getJsonObject(userVideo).getString("part"));
                } else
                    throw new NullPointerException();
                this.availableFileList.add(file);

            } catch (NullPointerException | FileNotFoundException e) {
                System.out.println(" 视频信息读取失败,原因可能如下："
                        + "\n" + "entry.json文件不存在"
                        + "\n" + "未知的entry.json文件，仅支持B站移动端缓存方式");
                System.out.println("\n是否跳过此视频？如果否则退出程序（请输入y/n）");
                String ch = scanner.nextLine();
                if (!ch.equalsIgnoreCase("y"))
                    ExitProgram.exit();
            }

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

    public ArrayList<File> getAvailableFileList() {
        return this.availableFileList;
    }
}
