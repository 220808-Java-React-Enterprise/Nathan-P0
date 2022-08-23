package com.revature.fff.ui.screens;

import com.revature.fff.services.InvalidInput;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.Console;
import com.revature.fff.ui.Rectangle;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Button;
import com.revature.fff.ui.components.FormField;
import com.revature.fff.ui.components.Frame;
import com.revature.fff.ui.components.Label;

public class Login extends Screen {
    FormField username, password;
    Button submit;
    public Login(ScreenManager sm) {
        super(sm);
        Frame form = new Frame().setTitle("Login").setBorder(true);
        form.setSize(10, 24);
        FormField username = new FormField(this, "Username").setMax(20);
        username.setPosition(1, 1);
        form.add(username);
        FormField password = new FormField(this, "Password").setMax(20);
        password.setPosition(4, 1);
        form.add(password);
        Button submit = new Button(this, "Login", () -> {
            try {
                UserService.login(username.getText(), password.getText());
                sm.setScreen(new MainMenu(sm));
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
        Label noacc = (Label) new Label("No account?");
        noacc.setPosition(form.getTop() + form.getHeight(), form.getLeft() + 1);
        add(noacc);
        Button signup = new Button(this, "Sign Up", () -> sm.setScreen(new Signup(sm)));
        signup.setPosition(noacc.getTop(), noacc.getWidth() + 1 + noacc.getLeft());
        add(signup);
        addFocusable(username).addFocusable(password).addFocusable(submit).addFocusable(signup);
    }
}
