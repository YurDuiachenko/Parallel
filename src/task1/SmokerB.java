package task1;

import java.awt.*;

import static task1.Main.*;
import static task1.res.Colors.BLUE;
import static task1.ui.Scene.*;

public class SmokerB implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                while (!tobacco && !matches) {
                    try {
                        smokerB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Paperist are making and smoking cigarette");
                tobacco = false;
                matches = false;
                paper = false;
                updateIcons();
                smoking();
                pusher.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private void smoking() {
        paperButton.setBackground(BLUE);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        paperButton.setBackground(Color.WHITE);
    }
}