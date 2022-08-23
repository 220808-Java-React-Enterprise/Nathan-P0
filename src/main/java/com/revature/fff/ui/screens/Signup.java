package com.revature.fff.ui.screens;

import com.revature.fff.services.InvalidInput;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.Console;
import com.revature.fff.ui.Rectangle;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Button;
import com.revature.fff.ui.components.FormField;
import com.revature.fff.ui.components.Frame;

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
        submit = new Button(this, "Sign Up", () -> {
            try {
                UserService.signup(username.getText(), password.getText());
                sm.setScreen(new SetStore(sm));
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
        add(form);
        addFocusable(username).addFocusable(password).addFocusable(submit);
    }
}
