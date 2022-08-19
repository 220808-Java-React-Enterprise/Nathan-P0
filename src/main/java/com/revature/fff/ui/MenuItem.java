package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MenuItem extends Component {
    private int index;
    private String text;
    private int position;
    private int accel;
    private IButton action;

    public MenuItem(@Nullable String text, int position, int accel, IButton action) {
        super(0, 0, 1, text.length());
        this.text = text == null ? "" : text;
        this.position = position;
        this.accel = accel;
        this.action = action;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setText(@Nullable String text) {
        this.text = text == null ? "" : text;
        setWidth(text.length());
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAccel(int accel) {
        this.accel = accel;
    }

    public void setAction(IButton action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public int getAccel() {
        return accel;
    }

    public IButton getAction() {
        return action;
    }

    @Override
    public void draw(@NotNull Console con, boolean active) {
        String idx = "" + (index + 1);
        con.setPosition(getTop(), getLeft() - idx.length() - 1);
        con.print(idx);
        con.print(active ? "_" : " ");
        con.print(text);
    }

    @Override
    public void process(String command) {
        action.execute();
    }
}
