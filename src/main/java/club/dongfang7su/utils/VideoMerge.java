package club.dongfang7su.utils;

import java.io.File;
import java.util.ArrayList;

public class VideoMerge {

    private final ArrayList<String> fileNameList = new ArrayList<>();

    public VideoMerge(File[] dirNumber, ArrayList<String> indexList, ArrayList<String> indexTitleList) {

        for (int i = 0; i < dirNumber.length; i++) {
            String indexVideo = "第" + indexList.get(i) + "集";
            String videoTitle = indexTitleList.get(i).replace(" ", "_");
            if (indexTitleList.get(i).equals("")) {
                this.fileNameList.add(indexVideo);
            } else this.fileNameList.add(indexVideo + "-" + videoTitle);
        }
    }

    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }
}
