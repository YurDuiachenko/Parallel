package task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

class IRunnable implements Runnable{
    int start;
    int end;
    int n;
    int[][] a;
    int[][] b;

    public IRunnable(int start, int end, int n, int[][] a, int[][] b) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
//        Method.phaser.register();
        for (int i = start; i < end; i++) {
            for (int j = 0; j < n; j++) {
                Method.c[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    Method.c[i][j] = Method.c[i][j] + a[i][k]*b[k][j];
                }
            }
        }
        System.out.println(Thread.currentThread().getName());
//        Method.phaser.arriveAndDeregister();
        try {
            Method.synchronization.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Method {

    private int thread_count = 10;
    public static Phaser phaser;
    public static CyclicBarrier synchronization;
    private int n;
    public static int c[][];
    private int x, y;

    private int[] indices;

    public Method(int thread_count, int n) {
        this.thread_count = thread_count;
        this.n = n;

        x = n/(thread_count-1);
        y = n%(thread_count-1);
        if (thread_count == 2) {
            x = n/(thread_count);
            y = x + n%(thread_count);
        }

        indices = new int[thread_count];

        for (int p = 0; p < thread_count; p++) {
            indices[p] = p*x;
        }
        synchronization = new CyclicBarrier(thread_count+1);
//
//        phaser = new Phaser();
    }

    public int[][] parallelMethodBarier(int[][] a, int[][] b, int[][] co) throws InterruptedException, BrokenBarrierException {
        c = co;

//        var run = new Thread[thread_count];
        Thread t = new Thread();
        for (int i = 0; i < thread_count; i++) {

            if(i == thread_count-1) {
                t = new Thread(new IRunnable(indices[i], indices[i] + y, n, a, b));
                t.start();
//                t.join();
            }
            else{
                new Thread(new IRunnable(indices[i], indices[i]+x, n, a, b)).start();
            }
        }
        synchronization.await();
        return c;
    }

    public int[][] parallelMethod(int[][] a, int[][] b, int[][] co) throws InterruptedException {
        c = co;

        int x = n/(thread_count-1);
        int y = n%(thread_count-1);

        int[] indices = new int[thread_count];

        for (int p = 0; p < thread_count; p++) {
            indices[p] = p*x;
        }

//        var run = new Thread[thread_count];
        var t = new Thread();
        for (int i = 0; i < thread_count; i++) {
            if(i == thread_count-1) {
                t = new Thread(new IRunnable(indices[i], indices[i] + y, n, a, b));
                t.start();

            }
            else{
                new Thread(new IRunnable(indices[i], indices[i]+x, n, a, b)).start();
            }
        }
        t.join();
        return c;
    }


    public int[][] parallelMethodPhaser(int[][] a, int[][] b, int[][] co) throws InterruptedException, BrokenBarrierException {
        c = co;
//        var run = new Thread[thread_count];
        Thread t = new Thread();
        Method.phaser.register();
        for (int i = 0; i < thread_count; i++) {
            if(i == thread_count-1) {
                t = new Thread(new IRunnable(indices[i], indices[i] + y, n, a, b));
                t.start();
//                t.join();
            }
            else{
                new Thread(new IRunnable(indices[i], indices[i]+x, n, a, b)).start();
            }
        }
        phaser.arriveAndAwaitAdvance();
//        phaser.arriveAndDeregister();
        return c;
    }

    public int[][] parallelMethodPool(int[][] a, int[][] b, int[][] co) throws InterruptedException, ExecutionException {
        c = co;

        ExecutorService service = Executors.newFixedThreadPool(thread_count);
        List<Future> futures = new ArrayList<>();
//        var thread = new Thread();
//        service.execute(new IRunnable());
//        var run = new Thread[thread_count];
        System.out.println(Arrays.toString(indices));

        for (int i = 0; i < thread_count; i++) {
            if(i == thread_count-1) {
                service.submit(new IRunnable(indices[i], indices[i]+y, n, a, b));
            }
            else {
                futures.add(service.submit((new IRunnable(indices[i], indices[i]+x, n, a, b))));
            }
        }
        System.out.println(Thread.activeCount());
//        var row = 0;
////        Thread[] threadi = new Thread[thread_count];
//        while (row < thread_count) {
////            threadi[row] = new Thread(run[row]);
////            threadi[row].start();
//            run[row].start();
//            row += 1;
//        }
////        threadi[0].join();
//        run[thread_count-1].join(1);
//        for(Future f : futures){
//            f.get();
//        }
        co = c;
        service.shutdown();
        return co;
    }



}

