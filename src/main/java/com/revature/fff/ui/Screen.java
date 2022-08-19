package com.revature.fff.ui;

import java.util.ArrayList;

public class Screen {

    protected ArrayList<Component> components = new ArrayList<>();
    protected Component active;
    public void draw() {
        Console con = Console.getInstance();
        for (Component c : components) {
            c.draw(con, c == active);
        }
    }

    public void destroy() {}
}
