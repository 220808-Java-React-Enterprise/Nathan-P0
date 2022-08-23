package com.revature.fff.ui.screens;

import com.revature.fff.dao.CategoryDAO;
import com.revature.fff.models.*;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Button;
import com.revature.fff.ui.components.Image;
import com.revature.fff.ui.components.Label;

import java.util.List;

public class MainMenu extends Screen {
    public MainMenu(ScreenManager sm) {
        super(sm);
        DBUser user = UserService.getActiveUser();
        if (user == null) {
            sm.setScreen(new Login(sm));
            return;
        }
        DBLocation location = user.getPreferred().get();
        Label welcome = new Label("Welcome " + user.getUsername() + " (Preferred store: " +
                                          (location != null ? String.format("%04d", location.getNumber()) : "<None>") +")");
        welcome.setPosition(1,1);
        add(welcome);
        List<DBCategory> categories = CategoryDAO.getInstance().getAll();
        try {
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 2; col++) {
                    DBCategory cat = categories.get(row * 2 + col);
                    DBImage imageData = cat.getImage().get();
                    Image image = new Image(imageData != null ? imageData.getData() : null,
                                            () -> { sm.setScreen(new ShowProductList(sm, cat)); });
                    image.setPosition(2 + row * 11, 5 + col * 21);
                    image.setSize(10, 20);
                    image.setTitle(cat.getName());
                    add(image);
                    addFocusable(image);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
        
        Button store = new Button(this, "Set Store", () -> { sm.setScreen(new SetStore(sm)); });
        store.setPosition(4, 50);
        add(store);
        addFocusable(store);

        Button history = new Button(this, "View History", () -> { sm.setScreen(new ShowHistory(sm)); });
        history.setPosition(6, 50);
        add(history);
        addFocusable(history);
        
        if (user.getRole().equals(Role.MANAGER)) {
            Label managerControls = new Label("Manager Controls");
            managerControls.setPosition(8, 50);
            add(managerControls);
            
            Button viewStores = new Button(this, "View Stores", () -> { sm.setScreen(new ShowManagedStores(sm)); } );
            viewStores.setPosition(10, 50);
            add(viewStores);
            addFocusable(viewStores);
        }
    }
}
