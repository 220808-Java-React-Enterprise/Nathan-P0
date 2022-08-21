package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

public class Frame extends Container {


    boolean border;
    String title;

    public Frame setBorder(boolean border) {
        this.border = border;
        return this;
    }

    public Frame setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void draw(@NotNull Console c) {
        super.draw(c);
        c.setMarginsRelative(bounds);
        c.setPosition(0, 0);
        int h = getHeight();
        int w = getWidth();
        if (border && w >= 2 && h >= 2) {
            c.print("╒");
            for (int i = 1; i < w - 1; i++) c.print("═");
            c.print(("╕\n"));
            for (int i = 1; i < h - 1; i++) {
                c.print("│");
                c.setCol(w - 1);
                c.print("│\n");
            }
            c.print("└");
            for (int i = 1; i < w - 1; i++) c.print("─");
            c.print(("┘"));
        }
        if (title != null && title.length() > 0) {
            c.setPosition(0, 1);
            c.print(title.substring(0, Math.min(title.length(), w - 2)));
        }
        c.restoreMargins();
    }

    @Override
    public void process(String command) {

    }
}
