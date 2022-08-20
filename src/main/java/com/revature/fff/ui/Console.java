package com.revature.fff.ui;

import java.util.Scanner;
import java.util.Stack;

public class Console {
    private char[][] screen;
    private byte[][] attrib;
    private byte[][] colors;
    private int row, col, height, width;
    Stack<Rectangle> margins = new Stack<>();
    private int mTop, mLeft, mBottom, mRight, mCR;
    private byte curAttrib;
    private byte fg, bg;
    Scanner in;
    private static boolean init = false;
    private static Console instance;

    public static Console initConsole(int h, int w) {
        if (instance == null) {
            instance = new Console(h, w);
            System.out.print("\u001B[?1049h");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.print("\u001B[?1049l")));
        }
        return instance;
    }

    public static Console getInstance() {
        return instance;
    }

    public Console(int h, int w) {
        in = new Scanner(System.in);
        java.io.Console c = System.console();
        height = h - 1;
        width = w;
        row = col = 0;
        screen = new char[height][width];
        attrib = new byte[height][width];
        colors = new byte[height][width];
        resetMargins();
        clear();
        if (!init) {
            init = true;
        }
        in = new Scanner(System.in);
    }

    public void setPosition(int r, int c) {
        row = r + mTop;
        col = c + mLeft;
    }

    public void setRow(int r) {
        row = r + mTop;
    }

    public void setCol(int c) {
        col = c + mLeft;
    }

    //region Margins
    public void setMargins(Rectangle bounds, int cr) {
        setMargins(bounds.getTop(), bounds.getLeft(), bounds.getBottom(), bounds.getRight(), cr);
    }

    public void setMarginsRelative(Rectangle bounds) {
        Rectangle r = bounds.clone();
        r.move(r.getTop() + mTop, r.getLeft() + mLeft);
        r.intersect(margins.peek());
        setMargins(r.getTop(), r.getLeft(), r.getBottom(), r.getRight(), mCR - margins.peek().getLeft());
    }

    public void setMargins(int t, int l, int b, int r, int cr) {
        margins.push(new Rectangle(
                t < 0 ? 0 : t,
                l < 0 ? 0 : l,
                b >= height ? height - 1 : b,
                r >= width ? width - 1 : r
        ));
        updateCachedMargins();
        setMarginReturn(cr);
    }

    public void resetMargins() {
        setMargins(0, 0, height - 1, width - 1, 0);
    }

    public boolean restoreMargins() {
        if (margins.size() > 1) {
            margins.pop();
            updateCachedMargins();
            return true;
        }
        return false;
    }

    public Rectangle getMargins() {
        return margins.peek().clone();
    }

    //Save locally as margins are checked frequently, and changed infrequently
    private void updateCachedMargins() {
        Rectangle cm = margins.peek();
        mTop = cm.getTop();
        mLeft = cm.getLeft();
        mBottom = cm.getBottom();
        mRight = cm.getRight();
    }

    public void setMarginReturn(int cr) {
        mCR = cr + mLeft;
    }
    //endregion

    public void setFgColor(byte c) {
        fg = c;
    }

    public void setBgColor(byte c) {
        bg = c;
    }

    public void print(String s) {
        if (s == null) return;
        for (char c : s.toCharArray()) {
            if (row > mBottom) return;
            if (c == '\n') {
                row++;
                col = mCR;
            } else {
                if (row >= mTop && col >= mLeft && col <= mRight) {
                    screen[row][col] = (char) c;
                    attrib[row][col] = curAttrib;
                    colors[row][col] = (byte) ((fg << 4) | (bg & 0xF));
                }
                col++;
            }
        }
    }

    public void clear() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                screen[r][c] = ' ';
                attrib[r][c] = 0;
                colors[r][c] = 0;
            }
        }
    }

    public void flush() {
        System.out.println();
        System.out.print("\u001B[;H");
        for (char[] line : screen) {
//            String s = new String(line);
            System.out.print(line);
//                System.out.write(s.getBytes(StandardCharsets.UTF_8));
            System.out.println();
        }
        System.out.print(" > ");
    }

    public String readLine() {
        return in.nextLine();
    }
}
