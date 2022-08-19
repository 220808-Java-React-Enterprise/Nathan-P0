package com.revature.fff.ui;

import com.revature.fff.services.InvalidInput;
import org.jetbrains.annotations.NotNull;

public class FField extends Component {
    String text;
    int maxSize;
    String label;
    Screen parent;
    IAction validator;

    public FField(Screen s) {
        super(0, 0, 2, 2);
        text = "";
        label = "";
        parent = s;
    }

    public FField(Screen s, String label) {
        super(0, 0, 2, label.length() + 2);
        text = "";
        this.label = label;
        parent = s;
    }

    public FField setMax(int max) {
        maxSize = max;
        return this;
    }

    public FField setText(String text) {
        this.text = text;
        if (text.length() > label.length()) setWidth(text.length() + 2);
        return this;
    }

    public FField setValidator(IAction v) {
        validator = v;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(@NotNull Console c, boolean active) {
        char[] temp = new char[getWidth()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = ' ';
        }
        System.arraycopy(text.toCharArray(), 0, temp, 1, text.length());
        if (active) {
            temp[0] = '<';
            temp[temp.length-1] = '>';
        }
        c.setPosition(getTop(), getLeft());
        c.print(new String(temp));
        c.print("\n " + label);
    }

    @Override
    public void process(String command) {
        if (command.length() > maxSize) {
            parent.setStatus("Text exceeds the maximum size of the field!");
        }
        else {
            try {
                validator.process(command);
                setText(command);
                parent.nextComp();
                parent.setStatus("");
            } catch (InvalidInput e) {
                parent.setStatus(e.getMessage());
            }
        }
    }
}
