package club.dongfang7su;

import club.dongfang7su.utils.*;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

/*
 *
 * 运行前需指定ffmpeg路径 @SaveFile
 *
 * */

public class Main {

    public static void main(String[] args) {
        String dirPath = null;
        Scanner sc = new Scanner(System.in);
        final String NVIDIA_GPU = "NVIDIA_GPU";
        final String INTEL_GPU = "INTEL_GPU";
        final String SOFTWARE_CPU = "SOFTWARE_CPU";
        final String DEFAULT = "DEFAULT";

        for (String arg : args) {
            if (new File(arg).exists()) {
                dirPath = arg.replace("\\", "\\\\");     //  读取拖拽文件路径
            }
        }
//        dirPath = "D:\\桌面\\1654875709";

        if (!new File("ffmpeg/ffmpeg.exe").exists()) {
            System.out.println("ffmpeg路径错误或程序不存在！！！");
            ExitProgram.exit();
        }

        DirManager dirManager = new DirManager();
        if (dirPath != null) {
            System.out.println("加载文件中");
            System.out.println();
            dirManager.setDirPath(dirPath);     //  初始化路径管理器，进入文件夹
            try {
                System.out.println("视频总数：" + Objects.requireNonNull(new File(dirPath).listFiles()).length);
            } catch (NullPointerException e) {
                System.out.println("此文件夹无法读取");
                System.exit(0);
            }

            System.out.println();
        }

        GetJsonData jsonData = new GetJsonData(dirManager.getDirPathList());    //  读取entry.json中的数据
        //  获取番剧名称、集数，标题
        VideoMerge videoMerge = new VideoMerge(
                jsonData.getIndexList(),
                jsonData.getIndexTitleList()
        );

//        System.out.println(jsonData.getAvailableFileList());

        System.out.println("加载完毕，请选择编码器：");
        System.out.println();
        System.out.println("输入对应数字回车即可：");
        System.out.println();
        System.out.println("1.  英伟达独显加速");
        System.out.println("2.  英特尔核显加速");
        System.out.println("3.  CPU软件编码");
        System.out.println("4.  快速无损合并");
        System.out.println();
        //  合并视频文件，输出同级目录下
        int ifNumber = sc.nextInt();
        long startTime = System.currentTimeMillis();
        switch (ifNumber) {
            case 1:
                new SaveFile(
                        dirPath,
                        jsonData.getAvailableFileList(),
                        jsonData.getVideoName(),
                        videoMerge.getFileNameList(),
                        NVIDIA_GPU
                );
                break;
            case 2:
                new SaveFile(
                        dirPath,
                        jsonData.getAvailableFileList(),
                        jsonData.getVideoName(),
                        videoMerge.getFileNameList(),
                        INTEL_GPU
                );
                break;
            case 3:
                new SaveFile(
                        dirPath,
                        jsonData.getAvailableFileList(),
                        jsonData.getVideoName(),
                        videoMerge.getFileNameList(),
                        SOFTWARE_CPU
                );
                break;
            case 4:
                new SaveFile(
                        dirPath,
                        jsonData.getAvailableFileList(),
                        jsonData.getVideoName(),
                        videoMerge.getFileNameList(),
                        DEFAULT
                );
                break;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\n处理完毕，耗时：" + (endTime - startTime) / 1000.0 + "秒\n");

        ExitProgram.exit();
    }
}
