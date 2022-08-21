package com.revature.fff.ui;

import com.revature.fff.dao.CategoryDAO;
import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBImage;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.UserService;

import java.util.List;

public class MainMenu extends Screen {
    public MainMenu(ScreenManager sm) {
        super(sm);
        DBUser user = UserService.getActiveUser();
        if (user == null) sm.setScreen(new Login(sm));
        Label welcome = new Label("Welcome " + user.getUsername() + " (" + user.getId() + ")");
        welcome.setPosition(1,1);
        components.add(welcome);
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
                    components.add(image);
                    addFocusable(image);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
}
