package com.revature.fff.ui;

import java.util.ArrayList;

public class Screen {

    protected ArrayList<Component> components = new ArrayList<>();
    protected int active;
    Label titlebar;
    Label statusbar;
    protected final ScreenManager manager;
    public Screen(ScreenManager sm) {
        manager = sm;
        active = 0;
        Console con = Console.getInstance();
        titlebar = new Label("Welcome to Fuyuki's Functional Furniture");
        titlebar.setBounds(new Rectangle(0, 0, 0, con.getMargins().getRight()));
        statusbar = new Label("");
        statusbar.setBounds(new Rectangle(con.getMargins().getBottom(), 0, con.getMargins().getRight(), con.getMargins().getBottom()));
    }

    public void preDraw() {}

    public void draw() {
        Console con = Console.getInstance();
        preDraw();
        for (Component c : components) {
            c.draw(con, c == components.get(active));
        }
        postDraw();
        titlebar.draw(con, false);
        statusbar.draw(con, false);
    }

    public void postDraw() {}

    public void processInput(String input) {
        getActive().process(input);
    }

    public void destroy() {}

    public void nextComp() {
        if (components.size() > 1) {
            active = (active + 1) % components.size();
        }
    }

    public void prevComp() {
        if (components.size() > 1) {
            active = (active - 1 + components.size()) % components.size();
        }
    }

    public Component getActive() {
        return components.get(active);
    }

    protected void layout() {
    }

    public void setStatus(String text) {
        statusbar.setText(text);
    }
}
