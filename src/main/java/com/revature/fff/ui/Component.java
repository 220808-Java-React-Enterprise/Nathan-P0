package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

public abstract class Component {
    Rectangle bounds;

    Component(int t, int l, int h, int w) {
        bounds = new Rectangle().setOrigin(t, l).setSize(h, w);
    }

    Component(@NotNull Rectangle bounds) {
        this.bounds = bounds.clone();
    }

    public void setTop(int top) {
        bounds.setTop(top);
    }

    public void setLeft(int left) {
        bounds.setLeft(left);
    }

    public void setHeight(int height) {
        bounds.setHeight(height);
    }

    public void setWidth(int width) {
        bounds.setWidth(width);
    }

    public int getTop() {
        return bounds.getTop();
    }

    public int getLeft() {
        return bounds.getLeft();
    }

    public int getHeight() {
        return bounds.getHeight();
    }

    public int getWidth() {
        return bounds.getWidth();
    }

    public abstract void draw(@NotNull Console c, boolean active);
}
