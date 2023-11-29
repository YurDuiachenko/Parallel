package task1;

import java.awt.*;

import static task1.Main.*;
import static task1.res.Colors.BLUE;
import static task1.ui.Scene.*;

public class SmokerC implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                while (!tobacco && !paper) {
                    try {
                        smokerC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Matcher is smoking a cigarette");

                tobacco = false;
                paper = false;
                matches = false;
                updateIcons();
                smoking();
                pusher.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private void smoking() {
        matchesButton.setBackground(BLUE);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        matchesButton.setBackground(Color.WHITE);
    }
}