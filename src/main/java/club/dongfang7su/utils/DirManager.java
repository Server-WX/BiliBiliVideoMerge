package club.dongfang7su.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/*
 *
 * 文件夹管理器负责处理次级子目录
 *
 * */
public class DirManager {

    private String dirPath;

    public DirManager() {
    }

    public File[] getDirPathList() {
        File path = new File(this.dirPath);
        if (!path.exists() || !path.isDirectory()) {
            System.out.println("路径不存在或不是文件夹！");
            return null;
        }

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(dirPath))) {
            if (!dirStream.iterator().hasNext()) {
                System.out.println("请勿导入空文件夹！");
            }
        } catch (IOException e) {
            System.out.println("路径异常：" + e);
        }

        if (Objects.requireNonNull(path.listFiles()).length == 0) {
            System.out.println("文件夹为空，请重新选择");
            ExitProgram.exit();
        }
        return path.listFiles();
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
