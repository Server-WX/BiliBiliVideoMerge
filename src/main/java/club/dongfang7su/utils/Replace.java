package club.dongfang7su.utils;

public class Replace {
    public static String replaceCharacters(String str) {
        // 定义需要屏蔽的特殊字符
        String specialCharacters = "[<>:\"/|?*]"; // 例如: < > : " / \ | ? *
        // 使用正则表达式替换特殊字符为空字符串
        return str.replaceAll(specialCharacters, "");
    }
}
