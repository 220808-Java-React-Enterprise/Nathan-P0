package com.revature.fff.ui;

public class ScreenManager implements Runnable {
    Screen current = null;
    Console console = Console.getInstance();

    @Override
    public void run() {
        while (current != null) {
            current.draw();
            console.flush();
            console.readLine();
        }
    }

    public void setScreen(Screen screen) {
        if (current != null) current.destroy();
        current = screen;
    }
}
