package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Screen {

    protected ArrayList<Component> components = new ArrayList<>();
    protected ArrayList<Component> focusable = new ArrayList<>();
    protected int active;
    //This component is a dummy component to avoid returning null
    private Component empty = new Component() {
        @Override
        public void draw(@NotNull Console c) {

        }

        @Override
        public void process(String command) {

        }
    };
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
            c.draw(con);
        }
        postDraw();
        titlebar.draw(con);
        statusbar.draw(con);
    }

    public void postDraw() {}

    public void processInput(String input) {
        getActive().process(input);
    }

    public void destroy() {}

    public void nextComp() {
        if (focusable.size() > 1) {
            focusable.get(active).setActive(false);
            active = (active + 1) % focusable.size();
            focusable.get(active).setActive(true);
        }
    }

    public void prevComp() {
        if (focusable.size() > 1) {
            focusable.get(active).setActive(false);
            active = (active - 1 + focusable.size()) % focusable.size();
            focusable.get(active).setActive(true);
        }
    }

    public Component getActive() {
        if (focusable.size() > active)
            return focusable.get(active);
        return empty;
    }

    protected void layout() {
    }

    public void setStatus(String text) {
        statusbar.setText(text);
    }

    public Screen addFocusable(Component c) {
        if (focusable.size() == active) c.setActive(true);
        focusable.add(c);
        return this;
    }
}
