package com.revature.fff.ui;

import org.jetbrains.annotations.NotNull;

public class Label extends Component {
    String text;

    public Label(String text) {
        super(0, 0, 1, text.length());
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(@NotNull Console c) {
        c.setPosition(getTop(), getLeft());
        c.print((active ? "<" : " ") + text + (active ? ">" : " "));
    }

    @Override
    public void process(String command) {
        //Labels don't have any action
    }
}
