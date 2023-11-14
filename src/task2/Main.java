package task2;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {
        int n = 883;
        Random random = new Random();

        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        int[][] C = new int[n][n];
        int[][] D = new int[n][n];

        int min = 2;
        int max = 1000;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = random.nextInt(max - min + 1) + min;
//                B[i][j] = random.nextInt(max - min + 1) + min;
                if(i == j) {
                    B[i][j] = 1;
                }
                else{
                    B[i][j] = 0;
                }
            }
        }
//        System.out.println(Arrays.deepToString(B));

        //
        long startTime = System.nanoTime();

        C = RegularMethod.regularMethod(A, B, C, n);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        printTime(duration);

        //
        startTime = System.nanoTime();

        D = new Method(6, n).parallelMethodBarier(A, B, D);

        endTime = System.nanoTime();
        duration = (endTime - startTime);
        printTime(duration);

        System.out.println(equal(A, C, n));
        System.out.println(equal(A, D, n));
        System.out.println(equal(C, D, n));

//        printMatrix();
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



    static boolean equal(int[][] A, int[][] B, int n){
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(A[i][j] == B[i][j]) {
                    count++;
                }
                else{
                    System.out.println(i + " " + j + " " + B[i][j]);
                }
            }
        }
        return (count == (n*n)) ? true: false;
    }
}