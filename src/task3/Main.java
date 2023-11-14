package task3;

import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Math.*;

public class Main {

    static double C;
    static double D;
    private static F f = (x) -> (sin(x) + cos(sqrt(3)*x) + 2); // Math.sqrt(x)
    static double a = 1;
    static double b = 100; //100

    public static double EPSILON = 0.0000000000000001;


    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {

        //
        long startTime = System.nanoTime();
//      new RegularMethod()
        C = new RegularMethod().quad(
                f,
                a,
                b,
                f.f(a),
                f.f(b),
                (f.f(a) + f.f(b)) * (b - a) / 2.0
        );

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        printTime(duration);


        //

        startTime = System.nanoTime();

        RecursiveTask<Double> recursiveTask = new Method(
            f,
            a,
            b,
            f.f(a),
            f.f(b),
            (f.f(a) + f.f(b)) * (b - a) / 2.0
        );
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        D = forkJoinPool.invoke(recursiveTask);

        endTime = System.nanoTime();
        duration = (endTime - startTime);
        printTime(duration);

        System.out.println(C);
        System.out.println(D);

    }

    static void printTime(long duration){
        System.out.println("Time kept in seconds: " + duration/1000000000);
        System.out.println("Time kept in millis:  " + duration/1000000);
        System.out.println("Time kept in nanos:   " + duration);
        System.out.println();
    }
        static void printMatrix(int[][] A){
        String s = "";
            for (int[] el: A) {
                s = "";
                for (int l: el) {
                    s = s + " "+  l;
                }
                System.out.println(s.trim());
            }
    }
}
