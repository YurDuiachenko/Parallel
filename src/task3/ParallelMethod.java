package task3;

import task2.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static task3.Main.EPSILON;
//import static task3.ParallelMethod.phaser;


public class ParallelMethod {
    int n;
    static ExecutorService pool;
    static int input;


    public ParallelMethod() {
        pool = new ScheduledThreadPoolExecutor(n);
    }

    public static double quad(
            F f,
            double left,
            double right,
            double fleft,
            double fright,
            double lrarea
    ) throws ExecutionException, InterruptedException, BrokenBarrierException {

        double mid = (left + right) / 2.0;
        double fmid = f.f(mid);
        double larea = (fleft + fmid) * (mid - left) / 2.0;
        double rarea = (fmid + fright) * (right - mid) / 2.0;
//        System.out.println("     "  + Thread.currentThread() + " " + Thread.activeCount());
        if (Math.abs((larea + rarea) - lrarea) > EPSILON) {
            // Future<Double> future_
            larea = pool.submit(new ICallable(f, left, mid, fleft, fmid, larea)).get();
            //Future<Double> future_
            rarea = pool.submit(new ICallable(f, mid, right, fmid, fright, rarea)).get();
        }
        return (larea + rarea);
    }
}

class ICallable implements Callable<Double> {
    F f;
    double left;
    double right;
    double fleft;
    double fright;
    double lrarea;

    public ICallable(F f, double left, double right, double fleft, double fright, double lrarea) {
        this.f = f;
        this.left = left;
        this.right = right;
        this.fleft = fleft;
        this.fright = fright;
        this.lrarea = lrarea;
    }

    @Override
    public Double call() throws Exception {
        double result = ParallelMethod.quad(
                f,
                left,
                right,
                fleft,
                fright,
                lrarea
        );
        return result;
    }
}