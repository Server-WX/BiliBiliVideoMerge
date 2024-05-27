package club.dongfang7su.utils;

import java.util.Scanner;

public class ExitProgram {
    //  回车退出程序
    public static void exit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n回车退出程序\n");
        scanner.nextLine();
        System.exit(0);
    }
}
