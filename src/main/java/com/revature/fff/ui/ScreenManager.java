package com.revature.fff.ui;

public class ScreenManager implements Runnable {
    Screen current = null;
    Console console = Console.getInstance();

    @Override
    public void run() {
        while (current != null) {
            console.clear();
            current.draw();
            console.flush();
            String input = console.readLine();
            if (input.length() > 0 && input.charAt(0) == ':') {
                if (input.length() > 1) {
                    char c = input.charAt(1);
                    if (c == '.') current.processInput("");
                    else if (c == '>') current.nextComp();
                    else if (c == '<') current.prevComp();
                }
            }
            else current.processInput(input);
        }
    }

    public void setScreen(Screen screen) {
        if (current != null) current.destroy();
        current = screen;
    }
}
