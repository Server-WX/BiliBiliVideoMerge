package club.dongfang7su.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveFile {
    public SaveFile(String dirPath, ArrayList<File> filesPath, String dirName, ArrayList<String> fileNameList, String encoder) {
        File path = new File(dirPath);
//        System.out.println(path.getPath() + " " + path.exists());
        File savePath = new File(path.getParentFile() + "\\" + Replace.replaceCharacters(dirName));
        File file = new File(dirPath);
        int allFilesNumber = Objects.requireNonNull(file.listFiles()).length;
        System.out.println("视频总数：" + allFilesNumber);
        System.out.println("实际导出数：" + filesPath.size());
//        System.out.println("错误数量：" + (allFilesNumber - filesPath.size()));
        if (path.exists() && !savePath.exists()) {
            if (savePath.mkdir()) {
                System.out.println("输出文件夹创建成功，文件将导出至：" + savePath);
            }
        }
//        System.out.println(savePath.getPath() + " " + savePath.exists());

        String ffmpegPath = "ffmpeg/ffmpeg.exe"; // TODO: 注意替换ffmpeg程序路径

        try {
            int second = 3;
            System.out.println();
            for (int i = second; i >= 0; i--) {
                System.out.printf("\r程序将于 " + i + " 秒后开始导出");
                Thread.sleep(1000);
            }
            System.out.println("\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] NVIDIA_GPU;
        String[] INTEL_GPU;
        String[] DEFAULT;

        for (int i = 0; i < filesPath.size(); i++) {
            String mediaFilePath = null;

            for (File dir : Objects.requireNonNull(filesPath.get(i).listFiles())) {
                if (dir.isDirectory()) {
                    mediaFilePath = filesPath.get(i) + "\\" + dir.getName() + "\\";
                }
            }

            String output = savePath + "\\" + Replace.replaceCharacters(fileNameList.get(i)) + ".mp4";
//            System.out.println(output);

            String videoFile = mediaFilePath + "video.m4s";
            String audioFile = mediaFilePath + "audio.m4s";

            NVIDIA_GPU = new String[]{
                    ffmpegPath,
                    "-hide_banner", "-hwaccel", "cuda",
                    "-loglevel", "error",
                    "-stats",
                    "-i", videoFile,
                    "-i", audioFile,
                    "-vcodec", "copy",
                    "-acodec", "copy",
                    output,
                    "-y"
            };
            INTEL_GPU = new String[]{
                    ffmpegPath,
                    "-hide_banner", "-hwaccel", "qsv",
                    "-loglevel", "error",
                    "-stats",
                    "-i", videoFile,
                    "-i", audioFile,
                    "-vcodec", "copy",
                    "-acodec", "copy",
                    output,
                    "-y"
            };
            DEFAULT = new String[]{
                    ffmpegPath,
                    "-hide_banner", "-hwaccel", "auto",
                    "-loglevel", "error",
                    "-stats",
                    "-i", videoFile,
                    "-i", audioFile,
                    "-vcodec", "copy",
                    "-acodec", "copy",
                    output,
                    "-y"
            };

            switch (encoder) {
                case "NVIDIA_GPU":
                    outputFile(NVIDIA_GPU);
                    break;
                case "INTEL_GPU":
                    outputFile(INTEL_GPU);
                    break;
                case "DEFAULT":
                    outputFile(DEFAULT);
                    break;
            }

        }
    }

    private static void outputFile(String[] command) {
        try {
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.printf("\rffmpeg状态: " + line);
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
