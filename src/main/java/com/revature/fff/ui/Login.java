package com.revature.fff.ui;

import com.revature.fff.services.InvalidInput;
import com.revature.fff.services.UserService;

public class Login extends Screen {
    FormField username, password;
    Button submit;
    public Login(ScreenManager sm) {
        super(sm);
        Frame form = new Frame().setTitle("Login").setBorder(true);
        form.setSize(10, 24);
        FormField username = new FormField(this, "Username").setMax(20).setValidator((s) -> {
            UserService.checkUsername(s);
        });
        username.setPosition(1, 1);
        form.add(username);
        FormField password = new FormField(this, "Password").setMax(20).setValidator((s) -> {
            UserService.checkPassword(s);
        });
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
        components.add(form);
        Label noacc = (Label) new Label("No account?");
        noacc.setPosition(form.getTop() + form.getHeight(), form.getLeft() + 1);
        components.add(noacc);
        Button signup = new Button(this, "Sign Up", () -> sm.setScreen(new Signup(sm)));
        signup.setPosition(noacc.getTop(), noacc.getWidth() + 1 + noacc.getLeft());
        components.add(signup);
        addFocusable(username).addFocusable(password).addFocusable(submit).addFocusable(signup);
    }
}
