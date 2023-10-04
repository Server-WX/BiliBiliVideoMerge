package club.dongfang7su.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveFile {
    public SaveFile(String dirPath, File[] filesPath, String dirName, ArrayList<String> fileNameList) {
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

        for (int i = 0; i < filesPath.length; i++) {
            String mediaFilePath = filesPath[i] + "\\80\\";
            String output = savePath + "\\" + fileNameList.get(i) + ".mp4";
            System.out.println(output);
            String videoFile = mediaFilePath + "video.m4s";
            String audioFile = mediaFilePath + "audio.m4s";

            String[] command = {"\"" + ffmpegPath + "\"" + " -i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -codec copy " + "\"" + output + "\""};
            System.out.println(Arrays.toString(command));
            try {
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
}
