package com.revature.fff.ui.components;

import com.revature.fff.ui.Console;
import org.jetbrains.annotations.NotNull;

public class Label extends Component {
    protected String text;

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
        c.print(text);
    }

    @Override
    public void process(String command) {
        //Labels don't have any action
    }
}
