package club.dongfang7su.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveFile {
    public SaveFile(String dirPath, File[] filesPath, String dirName, ArrayList<String> fileNameList, String encoder) {
        File path = new File(dirPath);
        System.out.println(path.getPath() + " " + path.exists());
        File savePath = new File(path.getParentFile() + "\\" + dirName);
        if (path.exists() && !savePath.exists()) {
            if (savePath.mkdir()) {
                System.out.println("文件夹创建成功");
            }
        }
        System.out.println(savePath.getPath() + " " + savePath.exists());

        String ffmpegPath = "ffmpeg/ffmpeg.exe"; // TODO: 注意替换ffmpeg程序路径

        String NVIDIA_GPU;
        String INTEL_GPU;
        String SOFTWARE_CPU;

        for (int i = 0; i < filesPath.length; i++) {
            String mediaFilePath = filesPath[i] + "\\" + folderName + "\\";
            String output = savePath + "\\" + fileNameList.get(i) + ".mp4";
            System.out.println(output);
            String videoFile = mediaFilePath + "video.m4s";
            String audioFile = mediaFilePath + "audio.m4s";

            NVIDIA_GPU = "\"" + ffmpegPath + "\"" + " -i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -codec copy -c:v h264_nvenc " + "\"" + output + "\"";
            INTEL_GPU = "\"" + ffmpegPath + "\"" + " -i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -codec copy -c:v h264_qsv " + "\"" + output + "\"";
            SOFTWARE_CPU = "\"" + ffmpegPath + "\"" + " -i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -codec copy -c:v libx264 " + "\"" + output + "\"";

            switch (encoder) {
                case "NVIDIA_GPU":
                    outputFile(NVIDIA_GPU, output);
                    break;
                case "INTEL_GPU":
                    outputFile(INTEL_GPU, output);
                    break;
                case "SOFTWARE_CPU":
                    outputFile(SOFTWARE_CPU, output);
                    break;
            }

        }
    }

    private static void outputFile(String command, String output) {
        try {
            System.out.println(command);
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            // 获取输出流
            InputStream inputStream = videoProcess.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 读取输出内容并打印
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = videoProcess.waitFor();
            if (exitCode == 0) {
                System.out.println("合并成功，文件：" + output);
            } else {
                System.out.println("合并失败，错误代码：" + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
