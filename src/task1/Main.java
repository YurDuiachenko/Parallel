package task1;

import task1.ui.Scene;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static boolean[] table = new boolean[3];
    public static boolean tobacco = table[0];
    public static boolean paper = table[1];
    public static boolean matches = table[2];

    static final Lock lock = new ReentrantLock();
    static final Condition pusher = lock.newCondition();
    static final Condition smokerA = lock.newCondition();
    static final Condition smokerB = lock.newCondition();
    static final Condition smokerC = lock.newCondition();

    public static void main(String[] args) {

        Scene scene = new Scene("Smokers problem");
        scene.setScene();

        new Thread(new SmokerA()).start();
        new Thread(new SmokerB()).start();
        new Thread(new SmokerC()).start();
        new Thread(new Pusher()).start();
    }
}