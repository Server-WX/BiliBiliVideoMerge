package club.dongfang7su;

import club.dongfang7su.utils.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

/*
 *
 * 程序主入口负责处理文件夹导入与交互
 *
 * */

public class Main {

    public static final String FFMPEG_PATH = "ffmpeg/ffmpeg.exe";

    private static final String NVIDIA_GPU = "NVIDIA_GPU";
    private static final String INTEL_GPU = "INTEL_GPU";
    private static final String DEFAULT = "DEFAULT";

    public static void main(String[] args) {

        try {
            File programDir = getProgramDirectory();

            String draggedPath = processDraggedArgs(args);
//            draggedPath = "D:\\桌面\\114476917461083";
            if (draggedPath == null) {
                System.out.println("错误：未拖拽有效文件夹！");
                ExitProgram.exit();
            } else {

                File ffmpegPath = safeResolveFile(new File(programDir, FFMPEG_PATH));

                System.out.println("正在导入的文件夹: " + draggedPath);
                System.out.println("FFmpeg路径: " + ffmpegPath.getAbsolutePath());

                userInteraction(new File(draggedPath), dirInit(draggedPath));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 获取程序所在目录（兼容JAR/EXE/IDE运行）
    private static File getProgramDirectory() throws URISyntaxException {
        //  动态获取当前 Java 程序所在的 JAR 文件或 class 文件目录的绝对路径，并将其转换为标准的 URI 格式。
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        // 如果是JAR文件，返回其所在目录；否则返回项目目录
        return jarFile.isFile() ? jarFile.getParentFile() : new File(System.getProperty("user.dir"));
    }

    // 处理拖拽参数（合并空格并验证路径存在性）
    private static String processDraggedArgs(String[] args) {
        if (args.length == 0) return null;
        // 合并参数以处理带空格的路径（例如 "D:/My Folder" -> ["D:/My", "Folder"]）
        File dir = new File(String.join(" ", args));
        //  当文件夹存在时返回绝对路径
        return dir.exists() ? dir.getAbsolutePath() : null;
    }

    // 安全解析文件路径（处理中文和空格）
    private static File safeResolveFile(File file) {
        // 使用URI自动处理编码问题（空格转为%20，中文转为UTF-8编码）
        URI uri = file.toURI();
        return new File(uri);
    }

    //  初始化路径
    private static GetJsonData dirInit(String dirPath) {
        DirManager dirManager = new DirManager();
        if (dirPath != null) {
            System.out.println("加载文件中....");
            dirManager.setDirPath(dirPath);     //  初始化路径管理器，进入文件夹
            System.out.println("加载完毕!");
            try {
                System.out.println("视频总数：" + Objects.requireNonNull(new File(dirPath).listFiles()).length);
            } catch (NullPointerException e) {
                System.out.println("此文件夹无法读取");
                ExitProgram.exit();
            }
            System.out.println();
        }

        return new GetJsonData(dirManager.getDirPathList());

    }

    private static void userInteraction(File dirPath, GetJsonData jsonData) {
        Scanner sc = new Scanner(System.in);
        VideoMerge videoMerge = new VideoMerge(
                jsonData.getIndexList(),
                jsonData.getIndexTitleList()
        );
        System.out.println("请选择要使用的解码器(不知道选哪个的请选择 自动)：");
        System.out.println("输入对应数字回车即可：\n");
        System.out.println("1.  英伟达独显加速");
        System.out.println("2.  英特尔核显加速");
        System.out.println("3.  自动选择");
        System.out.println();
        //  合并视频文件，输出同级目录下
        int ifNumber = sc.nextInt();
        System.out.println("为保证您的导出结果，接下来导出的过程请勿关闭程序(如需强制停止请同时按下键盘Ctrl键和C键)");
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
    }

}
