package com.base.job.test;

public class AppTest {
    public static void main(String[] args) {
        // add `javaconfig` to args
        String[] customArgs = new String[]{"javaconfig"};
        com.alibaba.dubbo.container.Main.main(args);
    }
}
