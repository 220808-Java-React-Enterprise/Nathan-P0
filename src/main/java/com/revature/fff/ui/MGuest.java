package com.revature.fff.ui;

public class MGuest extends Menu {
    public MGuest(ScreenManager sm) {
        super(sm);
        addItem(new MenuItem("Sign Up", 0, 0, () -> {manager.setScreen(new Login(sm));})).
        //addItem(new MenuItem("Log In", 0, 0, () -> {manager.setScreen(new Login(sm));})).
        layout();
    }
}
