package com.revature.fff.ui;

import com.revature.fff.services.InvalidInput;
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
            try {
                UserService.signup(username.getText(), password.getText());
                sm.setScreen(new Login(sm));
            }
            catch(InvalidInput e) {
                setStatus(e.getMessage());
            }
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
}
