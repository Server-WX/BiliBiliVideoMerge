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
        final String DEFAULT = "DEFAULT";

        for (String arg : args) {
            if (new File(arg).exists()) {
                dirPath = arg.replace("\\", "\\\\");     //  读取拖拽文件路径
            }
        }
//        dirPath = "D:\\桌面\\304800481";

        if (!new File("ffmpeg/ffmpeg.exe").exists()) {
            System.out.println("ffmpeg路径错误或程序不存在！！！");
            ExitProgram.exit();
        }

        DirManager dirManager = new DirManager();
        if (dirPath != null) {
            System.out.println("加载文件中....");
            dirManager.setDirPath(dirPath);     //  初始化路径管理器，进入文件夹
            System.out.println("加载完毕!");
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

        System.out.println("请选择要使用的解码器(不知道选哪个的请选择 自动)：");
        System.out.println("输入对应数字回车即可：\n");
        System.out.println("1.  英伟达独显加速");
        System.out.println("2.  英特尔核显加速");
        System.out.println("3.  自动选择");
        System.out.println();
        //  合并视频文件，输出同级目录下
        int ifNumber = sc.nextInt();
        System.out.println("为保证您的导出结果，接下来导出的过程请勿关闭程序(如需强制停止请同时按下键盘Ctrl键和C键)");
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
                        DEFAULT
                );
                break;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\n\n处理完毕，耗时：" + (endTime - startTime) / 1000.0 + "秒");

//        ExitProgram.exit();
    }
}
