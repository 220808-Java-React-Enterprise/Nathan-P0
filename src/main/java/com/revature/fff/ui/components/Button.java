package com.revature.fff.ui.components;

import com.revature.fff.ui.Console;
import com.revature.fff.ui.IButton;
import com.revature.fff.ui.screens.Screen;
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
