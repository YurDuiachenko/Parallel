package app;

import java.awt.*;

import static app.Main.*;
import static app.res.Colors.BLUE;
import static app.ui.Scene.*;

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