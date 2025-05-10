package club.dongfang7su.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

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
            int second = 5;
            System.out.println("\n程序将于" + second + "秒后开始导出\n");
            for (int i = second; i >= 0; i--) {
                System.out.print(i + " ");
                Thread.sleep(1000);
            }
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String NVIDIA_GPU;
        String INTEL_GPU;
        String DIRECTXAPI;
        String DEFAULT;

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

            String[] NV_GPU = {
                    ffmpegPath,
                    "-hide_banner", "-hwaccel", "cuda",
                    "-loglevel", "error",
                    "-stats",
                    "-i",videoFile,
                    "-i",audioFile,
                    "-vcodec","copy",
                    "-acodec","copy",
                    output
            };
            NVIDIA_GPU = "\"" + ffmpegPath + "\"" + " -hide_banner -hwaccel cuda " + "-i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -vcodec copy -acodec copy " + "\"" + output + "\"";
            INTEL_GPU = "\"" + ffmpegPath + "\"" + " -hide_banner -hwaccel qsv " + "-i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -vcodec copy -acodec copy " + "\"" + output + "\"";
            DIRECTXAPI = "\"" + ffmpegPath + "\"" + " -hide_banner -hwaccel dxva2 " + "-i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -vcodec copy -acodec copy " + "\"" + output + "\"";
            DEFAULT = "\"" + ffmpegPath + "\"" + " -hide_banner -hwaccel auto " + "-i " + "\"" + videoFile + "\"" + " -i " + "\"" + audioFile + "\"" + " -vcodec copy -acodec copy " + "\"" + output + "\"";

            switch (encoder) {
                case "NVIDIA_GPU":
                    outputFile(NVIDIA_GPU, output);
                    break;
                case "INTEL_GPU":
                    outputFile(INTEL_GPU, output);
                    break;
                case "DIRECTXAPI":
                    outputFile(DIRECTXAPI, output);
                    break;
                case "DEFAULT":
                    outputFile(DEFAULT, output);
                    break;
            }

        }
    }

    private static void outputFile(String command, String output) {
//        System.out.println(command);
        try {
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            // 获取输出流
            InputStream inputStream = videoProcess.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 读取输出内容并打印
            String line;
            while ((line = reader.readLine()) != null) {
                // 清空命令行
                System.out.print("\033[H\033[2J");
                System.out.flush();
                // 移动光标到命令行顶部
                System.out.print("\033[0;0H");
                // 输出固定显示的文字
                System.out.println("----------------------------------------");
                System.out.println("正在导出中，请勿关闭程序！！！！！！！！");
                System.out.println("----------------------------------------");

                // 输出日志
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
