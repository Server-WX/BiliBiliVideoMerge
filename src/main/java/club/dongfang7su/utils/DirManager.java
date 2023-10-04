package club.dongfang7su.utils;

import java.io.File;

public class DirManager {

    private String dirPath;

    public DirManager() {
    }

    public File[] getDirPathList() {

        File path = new File(this.dirPath);
        if (!path.exists()) {
            System.out.println("非法路径！请检查是否正确！");
            return null;
        }

        return path.listFiles();

    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
