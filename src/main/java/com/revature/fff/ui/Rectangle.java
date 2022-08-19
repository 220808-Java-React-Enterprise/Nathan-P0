package com.revature.fff.ui;

public class Rectangle {
    protected int top, bottom;
    protected int left, right;

    Rectangle() {
    }

    Rectangle(int t, int l, int b, int r) {
        setBounds(t, l, b, r);
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setHeight(int height) {
        this.bottom = this.top + height - 1;
    }

    public void setWidth(int width) {
        this.right = this.left + width - 1;
    }
    //region Chainable Setters
    public Rectangle setBounds(int t, int l, int b, int r) {
        top = t;
        left = l;
        bottom = b;
        right = r;
        return this;
    }

    public Rectangle setOrigin(int t, int l) {
        top = t;
        left = l;
        return  this;
    }

    public Rectangle setSize(int h, int w) {
        setHeight(h);
        setWidth(w);
        return this;
    }

    public Rectangle move(int t, int l) {
        bottom += (t - top);
        right += (l - left);
        top = t;
        left = l;
        return this;
    }
    //endregion

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getHeight() {
        return bottom - top + 1;
    }

    public int getWidth() {
        return right - left + 1;
    }

    public Rectangle clone() {
        return new Rectangle(top, left, bottom, right);
    }

    public Rectangle adjust(int amt) {
        top -= amt;
        left -= amt;
        bottom += amt;
        right += amt;
        return this;
    }

    public Rectangle intersect(Rectangle other) {
        if (top < other.top) top = other.top;
        if (left < other.left) left = other.left;
        if (bottom > other.bottom) bottom = other.bottom;
        if (right > other.right) right = other.right;
        return this;
    }

    public Rectangle union(Rectangle other) {
        if (top > other.top) top = other.top;
        if (left > other.left) left = other.left;
        if (bottom < other.bottom) bottom = other.bottom;
        if (right < other.right) right = other.right;
        return this;
    }
}
