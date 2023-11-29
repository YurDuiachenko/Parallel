package task1;

import java.awt.*;

import static task1.Main.*;
import static task1.res.Colors.BLUE;
import static task1.ui.Scene.*;

public class SmokerA implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                while (!paper && !matches) {
                    try {
                        smokerA.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Tabacoist are making and smoking cigarette");
                paper = false;
                matches = false;
                tobacco = false;
                updateIcons();
                smoking();
                pusher.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private void smoking() {
        tabacoButton.setBackground(BLUE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tabacoButton.setBackground(Color.WHITE);
    }
}