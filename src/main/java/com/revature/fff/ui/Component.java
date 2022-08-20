package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

public abstract class Component implements IAction {
    protected Rectangle bounds;
    protected boolean active;

    Component() {
        bounds = new Rectangle();
    }

    Component(int t, int l, int h, int w) {
        bounds = new Rectangle().setOrigin(t, l).setSize(h, w);
    }

    Component(@NotNull Rectangle bounds) {
        setBounds(bounds);
    }

    public Component setBounds(Rectangle bounds) {
        this.bounds = bounds.clone();
        return this;
    }

    public Component setPosition(int top, int left) {
        bounds.move(top, left);
        return this;
    }

    public Component setSize(int height, int width) {
        bounds.setSize(height, width);
        return this;
    }

    public Component setTop(int top) {
        bounds.setTop(top);
        return this;
    }

    public Component setLeft(int left) {
        bounds.setLeft(left);
        return this;
    }

    public Component setHeight(int height) {
        bounds.setHeight(height);
        return this;
    }

    public Component setWidth(int width) {
        bounds.setWidth(width);
        return this;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTop() {
        return bounds.getTop();
    }

    public int getLeft() { return bounds.getLeft(); }

    public int getHeight() {
        return bounds.getHeight();
    }

    public int getWidth() {
        return bounds.getWidth();
    }

    public abstract void draw(@NotNull Console c);
}
