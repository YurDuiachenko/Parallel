package task3;

import static task3.Main.EPSILON;

public class RegularMethod {

     public static double quad(
             F f,
             double left,
             double right,
             double fleft,
             double fright,
             double lrarea
     ) {
          double mid = (left + right) / 2.0;
          double fmid = f.f(mid);
          double larea = (fleft + fmid) * (mid - left) / 2.0;
          double rarea = (fmid + fright) * (right - mid) / 2.0;
          if (Math.abs((larea + rarea) - lrarea) > EPSILON){
               larea = quad(f, left, mid, fleft, fmid, larea);
               rarea = quad(f, mid, right, fmid, fright, rarea);
          }
//          System.out.println(larea + rarea);
          return (larea + rarea);
     }


     public static double regularMethodIter(F f, double a, double b, double n) {
          double fleft = f.f(a),
                  fright,
                  area = 0.0;
          double width = (b - a) / n;
          for (double x = (a + width); x <= b; x+=width) {
               fright = f.f(x);
               area = area + (fleft + fright) * width / 2.0;
               fleft = fright;
          }
          return area;
     }
}
