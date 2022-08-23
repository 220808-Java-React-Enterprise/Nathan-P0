package com.revature.fff.ui.components;

import com.revature.fff.ui.Console;
import com.revature.fff.ui.IButton;
import org.jetbrains.annotations.NotNull;

public class Image extends Frame {
    IButton action;
    String[] lines = new String[0];
    public Image(String data, IButton action) {
        this.action = action;
        if (data == null || data.length() < 2) return;
        int height = data.charAt(0) - '@';
        int width = data.charAt(1) - '@';
        if (height * width + 2 > data.length()) return;
        lines = new String[height];
        for (int row = 0; row < height; row++)
            lines[row] = data.substring(row * width + 2, row * width + 2 + width);
        setSize(height, width);
    }

    @Override
    public void draw(@NotNull Console c) {
        c.setMarginsRelative(bounds);
        c.setPosition(0, 0);
        for (int row = 0; row < lines.length; row++) {
            c.print(lines[row]);
            c.print("\n");
        }
        c.restoreMargins();
        super.draw(c);
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        border = active;
    }

    @Override
    public void process(String command) {
        if (action != null) action.execute();
    }
}
