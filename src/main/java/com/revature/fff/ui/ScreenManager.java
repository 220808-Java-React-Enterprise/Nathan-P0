package com.revature.fff.ui;

import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.UserService;

public class ScreenManager implements Runnable {
    Screen current = null;
    Console console = Console.getInstance();

    @Override
    public void run() {
        while (current != null) {
            console.clear();
            current.draw();
            console.flush();
            String input = console.readLine();
            if (input.length() > 0 && input.charAt(0) == ':') {
                if (input.length() > 1) {
                    for (char c: input.substring(1).toCharArray()) {
                        if (c == '.') current.processInput("");
                        else if (c == '>') current.nextComp();
                        else if (c == '<') current.prevComp();
                        else if (c == 'h') setScreen(new ShowHistory(this));
                        else if (c == 'm') setScreen(new MainMenu(this));
                        else if (c == 'l') {
                            UserService.logout();
                            setScreen(new Login(this));
                            break;
                        }
                        else if (c == 'c') {
                            DBUser user = UserService.getActiveUser();
                            if (user == null) {
                                setScreen(new Login(this));
                                break;
                            }
                            DBTransaction cart = user.getCart().get();
                            if (cart != null) setScreen(new ShowInvoice(this, cart, true));
                            break;
                        }
                    }
                }
            }
            else current.processInput(input);
        }
    }

    public void setScreen(Screen screen) {
        if (current != null) current.destroy();
        current = screen;
    }
}
