package task2;

import java.util.Arrays;
import java.util.stream.IntStream;

public class RegularMethod {

     public static int[][] regularMethod(int[][] a, int[][] b, int[][] c, int n){
          for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
                    c[i][j] = 0;
                    for (int k = 0; k < n; k++) {
                         c[i][j] = c[i][j] + a[i][k]*b[k][j];
                    }
               }
          }
          return c;
     }
}
