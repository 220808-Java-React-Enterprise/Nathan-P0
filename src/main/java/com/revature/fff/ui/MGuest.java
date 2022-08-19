package com.revature.fff.ui;

public class MGuest extends Menu {
    public MGuest() {
        addItem(new MenuItem("Sign Up", 0, 0, () -> {})).
        addItem(new MenuItem("Log In", 0, 0, () -> {})).
        layout();
    }
}
