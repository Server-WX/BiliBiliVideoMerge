package club.dongfang7su.utils;

import java.io.File;
import java.util.ArrayList;

public class VideoMerge {

    private final ArrayList<String> fileNameList = new ArrayList<>();

    public VideoMerge(ArrayList<String> indexList, ArrayList<String> indexTitleList) {

        for (String index : indexList) {
            String indexVideo = "第" + index + "集";
            String videoTitle = indexTitleList.get(indexList.indexOf(index)).replace(" ", "_");
            if (indexTitleList.get(indexList.indexOf(index)).isEmpty()) {
                this.fileNameList.add(indexVideo);
            } else this.fileNameList.add(indexVideo + "-" + videoTitle);
        }
    }

    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }
}
