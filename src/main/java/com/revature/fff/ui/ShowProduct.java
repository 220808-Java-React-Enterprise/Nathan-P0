package com.revature.fff.ui;

import com.revature.fff.models.DBImage;
import com.revature.fff.models.DBItem;
import com.revature.fff.services.InvalidInput;

public class ShowProduct extends Screen {
    public ShowProduct(ScreenManager sm, DBItem item) {
        super(sm);
        DBImage imageData = item.getImage().get();
        Image image = new Image(imageData != null ? imageData.getData() : null, () -> {});
        image.setPosition(3, 5);
        components.add(image);
        
        Frame rightPanel = new Frame();
        rightPanel.setPosition(5, 40);
        rightPanel.setSize(10, 35);
        components.add(rightPanel);
        
        Label name = new Label(item.getName());
        name.setPosition(0, 0);
        rightPanel.add(name);
        
        Label price = new Label(item.getDisplayPrice());
        price.setPosition(2, 0);
        rightPanel.add(price);
        
        FormField qty = new FormField(this, "Quantity").setMax(3).setValidator((s) -> {
            if (!s.matches("^[0-9]{1,3}$"))
                throw new InvalidInput("Please enter a number between 0 and 999");
        });
        qty.setPosition(4, 0);
        rightPanel.add(qty);
        addFocusable(qty);
        
        Button addCart = new Button(this, "Add To Cart", () -> {});
        addCart.setPosition(7, 0);
        rightPanel.add(addCart);
        addFocusable(addCart);

        Button buyNow = new Button(this, "Buy Now", () -> {});
        buyNow.setPosition(9, 0);
        rightPanel.add(buyNow);
        addFocusable(buyNow);

        Label desc0 = new Label("Description:");
        desc0.setPosition(17, 5);
        components.add(desc0);
        Label desc = new Label(item.getDesc());
        desc.setPosition(18, 5);
        components.add(desc);
    }
}
