package club.dongfang7su;

import club.dongfang7su.utils.DirManager;
import club.dongfang7su.utils.GetJsonData;
import club.dongfang7su.utils.SaveFile;
import club.dongfang7su.utils.VideoMerge;

import java.io.File;
import java.util.Scanner;

/*
 *
 * 运行前需指定ffmpeg路径 @SaveFile
 *
 * */

public class Main {

    public static void main(String[] args) {
        String dirPath = null;

        for (String arg : args) {
            if (new File(arg).exists()) {
                dirPath = arg.replace("\\", "\\\\");     //  读取拖拽文件路径
            }
        }
//        dirPath = "D:\\桌面\\我的三体-章北海传";
        DirManager dirManager = new DirManager();
        dirManager.setDirPath(dirPath);     //  初始化路径管理器，进入文件夹

        GetJsonData jsonData = new GetJsonData(dirManager.getDirPathList());    //  读取entry.json中的数据
        //  获取番剧名称、集数，标题
        VideoMerge videoMerge = new VideoMerge(
                dirManager.getDirPathList(),
                jsonData.getIndexList(),
                jsonData.getIndexTitleList()
        );

        //  合并视频文件，输出同级目录下
        new SaveFile(
                dirPath,
                dirManager.getDirPathList(),
                jsonData.getVideoName(),
                videoMerge.getFileNameList()
        );

        Scanner sc = new Scanner(System.in);    //  回车退出程序
        sc.nextLine();
    }
}
