package com.mikhraly;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class pomodoro {

    static boolean test = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Введите команду");
        String[] cmd = new Scanner(System.in).nextLine().split(" ");

        int workMin = 25;
        int breakMin = 5;
        int count = 1;
        boolean isCallHelp = false;
        int sizePrint = 30;

        for (int i = 0; i < cmd.length; i++) {
            switch (cmd[i]) {
                case "-help" -> {
                    printHelpMsg();
                    isCallHelp = true;
                }
                case "-w" -> workMin = Integer.parseInt(cmd[++i]);
                case "-b" -> breakMin = Integer.parseInt(cmd[++i]);
                case "-count" -> count = Integer.parseInt(cmd[++i]);
                case "-t" -> test = true;
            }
        }

        if (!isCallHelp) {
            System.out.printf("Работаем %d min, отдыхаем %d min, повторяем %d раз\n",
                    workMin, breakMin, count);

            long startTime = System.currentTimeMillis();
            for (int i=0; i < count; i++) {
                timer(workMin, breakMin, sizePrint);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Таймер истек: " + (endTime - startTime) / (1000 * 60) + " min");
        }

    }

    public static void timer(int workTime, int breakTime, int size) throws InterruptedException {
        printProgress("Время работать: ", workTime, size);
        printProgress("Время отдыхать: ", breakTime, size);
    }

    private static void printHelpMsg() {
        System.out.println("\n\nСделай свое время более продуктивным:");
        System.out.println("-w <time>: время работы");
        System.out.println("-b <time>: время отдыха");
        System.out.println("-count <count>: количество итераций");
        System.out.println("--help: меню помощи\n");
    }

    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length = 60 * time / size;
        int rep = 60 * time / length;
        int stretchb = size / (3 * time);

        for (int i = 0; i <= rep; i++) {
            double x = i;
            x = 1.0 / 3.0 * x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time * stretchb;
            double percent = (x/w) * 1000;
            x /= stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;
            System.out.print(process + percent + "% " +
                    (" ").repeat(5 - (String.valueOf(percent).length())) + "[" +
                    ("#").repeat(i) + ("-").repeat(rep - i) +
                    "]    ( " + x + "min / " + time + "min )" + "\r");
            if (!test) {
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();
    }

}
