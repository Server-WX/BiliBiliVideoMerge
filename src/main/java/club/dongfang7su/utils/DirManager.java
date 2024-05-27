package club.dongfang7su.utils;

import java.io.File;
import java.util.Objects;

public class DirManager {

    private String dirPath;

    public DirManager() {
    }

    public File[] getDirPathList() {
        File path = new File(this.dirPath);
        if (!path.exists()) {
            return null;
        }
        if (Objects.requireNonNull(path.listFiles()).length == 0){
            System.out.println("文件夹为空，请重新选择");
            ExitProgram.exit();
        }
        return path.listFiles();
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
