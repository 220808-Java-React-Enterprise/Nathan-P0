package com.revature.fff.ui;

import com.revature.fff.models.DBImage;
import com.revature.fff.models.DBInventory;
import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.services.InvalidInput;
import com.revature.fff.services.ItemService;
import com.revature.fff.services.TransactionService;

public class ShowProduct extends Screen {
    public ShowProduct(ScreenManager sm, DBInventory inv, DBItem item, boolean edit) {
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
        if (edit) qty.setText("" + inv.getQuantity());
        rightPanel.add(qty);
        addFocusable(qty);
        
        if (edit) {
            Button editQty = new Button(this, "Change Quantity", () -> {
                ItemService.updateInventory(inv, Integer.parseInt(qty.getText()));
                sm.setScreen(new ShowInventory(sm, inv.getLocation().get()));
            });
            editQty.setPosition(7, 0);
            rightPanel.add(editQty);
            addFocusable(editQty);
        }
        else {
            Button addCart = new Button(this, "Add To Cart", () -> {
                DBTransaction transaction = TransactionService.getTransaction(true);
                TransactionService.addItem(transaction, item, Integer.parseInt(qty.getText()));
                setStatus("Item added successfully.");
            });
            addCart.setPosition(7, 0);
            rightPanel.add(addCart);
            addFocusable(addCart);

            if (inv.getQuantity() > 0) {
                Button buyNow = new Button(this, "Buy Now", () -> {
                    DBTransaction transaction = TransactionService.getTransaction(false);
                    TransactionService.addItem(transaction, item, Integer.parseInt(qty.getText()));
                    TransactionService.finalize(transaction);
                    sm.setScreen(new ShowInvoice(sm, transaction, false));
                });
                buyNow.setPosition(9, 0);
                rightPanel.add(buyNow);
                addFocusable(buyNow);
            }
            else {
                Label noStock = new Label("OUT OF STOCK");
                noStock.setPosition(9, 0);
                rightPanel.add(noStock);
            }
        }
        
        Label desc0 = new Label("Description:");
        desc0.setPosition(17, 5);
        components.add(desc0);
        Label desc = new Label(item.getDesc());
        desc.setPosition(18, 5);
        components.add(desc);
    }
}
