package com.revature.fff.ui;

public class Button extends Label {

    Screen parent;
    IAction action;

    public Button(Screen s, String text, IAction action) {
        super(text);
        parent = s;
        this.action = action;
    }

    @Override
    public void process(String command) {
        action.process(command);
    }
}
