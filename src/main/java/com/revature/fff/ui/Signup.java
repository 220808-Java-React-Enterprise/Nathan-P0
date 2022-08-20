package com.revature.fff.ui;

import com.revature.fff.services.UserService;

public class Signup extends Screen {
    FormField username, password;
    Button submit;
    public Signup(ScreenManager sm) {
        super(sm);
        Frame form = new Frame().setTitle("Sign Up").setBorder(true);
        form.setSize(10, 24);
        username = new FormField(this, "Username").setMax(20).setValidator((s) -> {
            UserService.checkUsername(s);
        });
        username.setPosition(1, 1);
        form.add(username);
        password = new FormField(this, "Password").setMax(20).setValidator((s) -> {
            UserService.checkPassword(s);
        });
        password.setPosition(4, 1);
        form.add(password);
        submit = new Button(this, "Sign Up", (s) -> {
            UserService.signup(username.getText(), password.getText());
        });
        submit.setPosition(7, 1);
        form.add(submit);
        Rectangle bounds = Console.getInstance().getMargins();
        int top = (bounds.getHeight() - form.getHeight()) / 2;
        int left = (bounds.getWidth() - form.getWidth()) / 2;
        form.setPosition(top, left);
        components.add(form);
        addFocusable(username).addFocusable(password).addFocusable(submit);
    }

    @Override
    protected void layout() {
//        super.layout();
//        int width = 0;
//        int height = 0;
//        for (Component c : components) {
//            width = Math.max(width, c.getWidth());
//            c.bounds.move(height, c.getLeft());
//            height = height + 1 + c.getHeight();
//        }
//        height--;
//        Rectangle bounds = Console.getInstance().getMargins();
//        int left = (bounds.getWidth() - width) / 2;
//        Rectangle inner = new Rectangle();
//        inner.setOrigin(5, left);
//        inner.setSize(height, width);
//        Console.getInstance().setMargins(inner, 0);
    }

    @Override
    public void preDraw() {
//        layout();
    }

    @Override
    public void postDraw() {
//        Console.getInstance().restoreMargins();
    }
}
