package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelMethod {

    public static int[][] parallelMethod(int[][] a, int[][] b, int[][] c, int n) throws InterruptedException {
        int i = 0;
        int t = 10;
        Thread thread = new Thread();
        while (i < n) {
            int finalI = i;
            if (i >= (n / t) * t) t = n % t;
            for (int l = 0; l < t; l++) {
                int finalL = l;
                thread = new Thread() {
                    @Override
                    public void run() {
                        for (int j = 0; j < n; j++) {
                            c[finalI + finalL][j] = 0;
                            for (int k = 0; k < n; k++) {
                                c[finalI + finalL][j] = c[finalI + finalL][j] + a[finalI + finalL][k] * b[k][j];
                            }
                        }
//                        System.out.println(currentThread().getName() + " Done");
                    }
                };
                thread.start();
//                System.out.println(thread.getName() + thread.isAlive());

            }
//            System.out.println(thread.getName() + " " +thread.isAlive());
            thread.join();
            i += t;
        }
        return c;
    }
    public static int[][] parallelMethodExecuter(int[][] a, int[][] b, int[][] c, int n) throws InterruptedException, ExecutionException {
        int i = 0;
        int t = 10;
        ExecutorService service = null;

//        service = Executors.newSingleThreadExecutor();  // создать один фоновый поток, который получит задачу
        service = Executors.newFixedThreadPool(t); // создать несколько фоновых потоков, которые будут ожидать задачи

        Thread thread = new Thread();
        while (i < n) {
            int finalI = i;
            if (i >= (n / t) * t) t = n % t;
            for (int l = 0; l < t; l++) {
                int finalL = l;
                Future procedureFuture = service.submit(
                thread = new Thread() {
                    @Override
                    public void run() {
                        for (int j = 0; j < n; j++) {
                            c[finalI + finalL][j] = 0;
                            for (int k = 0; k < n; k++) {
                                c[finalI + finalL][j] = c[finalI + finalL][j] + a[finalI + finalL][k] * b[k][j];
                            }
                        }
                    }
                });
                procedureFuture.get();
            }
            i += t;
        }
        service.shutdown();
        return c;
    }

    public static int[][] parallelMethodAsync(int[][] a, int[][] b, int[][] c, int n) throws InterruptedException, ExecutionException {
        int i = 0;
        int t = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(t);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CompletableFuture<Void> future1 = null;

        while (i < n) {
            int finalI = i;
            if (i >= (n / t) * t) t = n % t;
            for (int l = 0; l < t; l++) {
                int finalL = l;
                future1 = CompletableFuture.runAsync(
                         new Thread() {
                            @Override
                            public void run() {
                                for (int j = 0; j < n; j++) {
                                    c[finalI + finalL][j] = 0;
                                    for (int k = 0; k < n; k++) {
                                        c[finalI + finalL][j] = c[finalI + finalL][j] + a[finalI + finalL][k] * b[k][j];
                                    }
                                }
                            }
                        }, executorService);
                futures.add(future1);
            }
            i += t;
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return c;
    }

//    public static int[][] parallelMethodPool(int[][] a, int[][] b, int[][] c, int n) throws InterruptedException {
//
//        int i = 0;
//        int t = 1;
//        Thread thread = new Thread();
//        while (i < n) {
//            for (int j = 0; j < n; j++) {
//                c[i][j] = 0;
//                for (int k = 0; k < n; k++) {
//                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
//                }
//            }
//            i += t;
//        }
//        return c;
//    }
}
