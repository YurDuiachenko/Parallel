package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static task3.Main.EPSILON;


public class Method extends RecursiveTask<Double> {
    F f;
    double left;
    double right;
    double fleft;
    double fright;
    double lrarea;

    public Method(F f, double left, double right, double fleft, double fright, double lrarea) {
        this.f = f;
        this.left = left;
        this.right = right;
        this.fleft = fleft;
        this.fright = fright;
        this.lrarea = lrarea;
    }

    @Override
    protected Double compute() {

        double mid = (left + right) / 2.0;
        double fmid = f.f(mid);
        double larea = (fleft + fmid) * (mid - left) / 2.0;
        double rarea = (fmid + fright) * (right - mid) / 2.0;

        if (Math.abs((larea + rarea) - lrarea) > EPSILON){

            var l = new Method(f, left, mid, fleft, fmid, larea);
            var r = new Method(f, mid, right, fmid, fright, rarea);

            l.fork();
            r.fork();

            larea = l.join();
            rarea = r.join();
        }

        return (larea + rarea);
    }
}