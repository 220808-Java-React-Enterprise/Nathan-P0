package com.revature.fff.ui;

import com.revature.fff.services.UserService;

public class Signup extends Screen {
    FField username, password;
    Button submit;
    public Signup(ScreenManager sm) {
        super(sm);
        username = new FField(this, "Username").setMax(20).setValidator((s) -> {
            UserService.checkUsername(s);
        });
        components.add(username);
        password = new FField(this, "Password").setMax(20).setValidator((s) -> {
            UserService.checkPassword(s);
        });
        components.add(password);
        submit = new Button(this, "Sign Up", (s) -> {
            UserService.signup(username.getText(), password.getText());
        });
        components.add(submit);
        //layout();
    }

    @Override
    protected void layout() {
        super.layout();
        int width = 0;
        int height = 0;
        for (Component c : components) {
            width = Math.max(width, c.getWidth());
            c.bounds.move(height, c.getLeft());
            height = height + 1 + c.getHeight();
        }
        height--;
        Rectangle bounds = Console.getInstance().getMargins();
        int left = (bounds.getWidth() - width) / 2;
        Rectangle inner = new Rectangle();
        inner.setOrigin(5, left);
        inner.setSize(height, width);
        Console.getInstance().setMargins(inner, 0);
    }

    @Override
    public void preDraw() {
        layout();
    }

    @Override
    public void postDraw() {
        Console.getInstance().restoreMargins();
    }
}
