package org.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreadTest {

    // 模拟的任务：每个任务休眠 1 秒
    private static void simulateTask(int taskId) {
        try {
            System.out.println("Task " + taskId + " started on " + Thread.currentThread());
            Thread.sleep(1000); // 模拟阻塞 I/O
            System.out.println("Task " + taskId + " finished on " + Thread.currentThread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 使用虚拟线程
    private static void runVirtualThreads(int taskCount) throws InterruptedException, ExecutionException {
        //ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        // 正式 API
        List<Future<?>> futures = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < taskCount; i++) {
            int taskId = i;
            futures.add(executor.submit(() -> simulateTask(taskId)));
        }

        // 等待所有任务完成
        for (Future<?> f : futures) {
            f.get();
        }
        long end = System.currentTimeMillis();
        executor.shutdown();
        System.out.println("Virtual threads total time: " + (end - start) + " ms");
    }

    // 使用普通平台线程
    private static void runPlatformThreads(int taskCount) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < taskCount; i++) {
            int taskId = i;
            Thread t = new Thread(() -> simulateTask(taskId));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("Platform threads total time: " + (end - start) + " ms");
    }

    public static void main(String[] args) throws Exception {
        int taskCount = 100; // 可以调整任务数量，观察效果

        System.out.println("=== Virtual Thread Test ===");
        runVirtualThreads(taskCount);

        System.out.println("\n=== Platform Thread Test ===");
        runPlatformThreads(taskCount);
    }
}