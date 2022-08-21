package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

public class Button extends Label {

    Screen parent;
    IButton action;

    public Button(Screen s, String text, IButton action) {
        super(text);
        parent = s;
        this.action = action;
        setWidth(getWidth() + 2);
    }

    public void draw(@NotNull Console c) {
        c.setPosition(getTop(), getLeft());
        c.print((active ? "<" : " ") + text + (active ? ">" : " "));
    }

    @Override
    public void process(String command) {
        action.execute();
    }
}
