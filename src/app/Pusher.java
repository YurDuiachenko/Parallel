package app;

import java.util.Random;

import static app.Main.*;
import static app.ui.Scene.*;

public class Pusher implements Runnable {
    private Random rnd = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                while ((tobacco && paper) || (tobacco && matches) || (paper && matches)) {
                    try {
                        pusher.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int temp = rnd.nextInt(3);
                if (temp == 0) {
                    tobacco = false;
                    paper = true;
                    matches = true;
                    System.out.println("Pusher is pushing paper and matches");
                    updateIcons();
                    pushing();
                    smokerA.signal();
                }
                if (temp == 1) {
                    tobacco = true;
                    paper = false;
                    matches = true;
                    System.out.println("Pusher is pushing tobacco and matches");
                    updateIcons();
                    pushing();
                    smokerB.signal();
                }
                if (temp == 2) {
                    tobacco = true;
                    paper = true;
                    matches = false;
                    System.out.println("Pusher is pushing tobacco and paper");
                    updateIcons();
                    pushing();
                    smokerC.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private void pushing() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}