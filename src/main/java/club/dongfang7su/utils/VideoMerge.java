package club.dongfang7su.utils;

import java.util.ArrayList;

/*
 *
 * 处理视频文件名
 *
 * */

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
        //  返回文件名列表
        return fileNameList;
    }
}
