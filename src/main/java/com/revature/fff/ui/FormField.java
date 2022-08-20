package com.revature.fff.ui;

import com.revature.fff.services.InvalidInput;
import org.jetbrains.annotations.NotNull;

public class FormField extends Component {
    String text;
    int maxSize;
    String label;
    Screen parent;
    IAction validator;

    public FormField(Screen s) {
        super(0, 0, 2, 2);
        text = "";
        label = "";
        parent = s;
    }

    public FormField(Screen s, String label) {
        super(0, 0, 2, label.length() + 2);
        text = "";
        this.label = label;
        parent = s;
    }

    public FormField setMax(int max) {
        maxSize = max;
        if (max + 2 > getWidth()) setWidth(max + 2);
        return this;
    }

    public FormField setText(String text) {
        this.text = text;
        return this;
    }

    public FormField setValidator(IAction v) {
        validator = v;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(@NotNull Console c) {
        char[] temp = new char[maxSize + 2];
        for (int i = 1; i < temp.length - 1; i++) {
            temp[i] = '_';
        }
        System.arraycopy(text.toCharArray(), 0, temp, 1, text.length());
        if (active) {
            temp[0] = '<';
            temp[temp.length-1] = '>';
        }
        else {
            temp[0] = temp[temp.length-1] = ' ';
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
