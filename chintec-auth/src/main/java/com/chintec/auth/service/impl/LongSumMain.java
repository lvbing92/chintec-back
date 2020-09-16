package com.chintec.auth.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/2 16:04
 */
public class LongSumMain {
    //获取逻辑处理器数量
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final long NPS = (1000L * 1000 * 1000);
    static long calcSum;
    static final boolean reportSteals = true;

    public static void main(String[] args) throws Exception {
        int[] array = Utils.buildRandomIntArray(20000000);
        System.out.println("cpu‐num:" + NCPU);

        //单线程下计算数组数据总和  
        long l = System.currentTimeMillis();
        calcSum = seqSum(array);
        long l1 = System.currentTimeMillis();
        System.out.println("seq sum=" + calcSum + " ________用时：" + (l1 - l));
        long l3 = System.currentTimeMillis();
        //采用fork/join方式将数组求和任务进行拆分执行，最后合并结果
        LongSum ls = new LongSum(array, 0, array.length);
        ForkJoinPool fjp = new ForkJoinPool(4);
        //使用的线程数
        ForkJoinTask<Long> result = fjp.submit(ls);
        long l2 = System.currentTimeMillis();
        System.out.println("forkjoin sum=" + result.get() + "_________用时: " + (l2 - l3));
        System.out.println("isDone:"+result.isDone());
        fjp.shutdown();

    }

    private static long seqSum(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private static class Utils {
        static int[] buildRandomIntArray(int i) {
            List<Integer> intArrays = new ArrayList<>();
            for (int a = 0; a <= i; a++) {
                intArrays.add((int) (Math.random() * 10));
            }
            return intArrays.stream().mapToInt(Integer::valueOf).toArray();
        }

    }

}

