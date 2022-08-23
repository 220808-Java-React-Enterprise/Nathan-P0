package com.revature.fff.ui;

import com.revature.fff.models.DBTransEntry;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.Price;
import com.revature.fff.services.TransactionService;

import java.util.Collection;
import java.util.List;

public class ShowNoStock extends Screen {
    public ShowNoStock(ScreenManager sm, DBTransaction transaction, List<DBTransEntry> entries) {
        super(sm);
        Label msg = new Label("The following items are out of stock :(");
        msg.setPosition(2, 5);
        components.add(msg);
        Label msg2 = new Label("Remove items from order or Cancel order?");
        msg2.setPosition(3, 5);
        components.add(msg2);

        Button remove = new Button(this, "Remove", () -> {
            TransactionService.removeItems(entries);
            List<DBTransEntry> nostock = TransactionService.finalize(transaction);
            if (nostock == null) sm.setScreen(new ShowInvoice(sm, transaction, false));
            else sm.setScreen(new ShowNoStock(sm, transaction, nostock));
        } );
        remove.setPosition(5, 5);
        components.add(remove);
        addFocusable(remove);

        Button cancel = new Button(this, "Cancel", () -> {
            TransactionService.cancel(transaction);
        } );
        cancel.setPosition(5, 15);
        components.add(cancel);
        addFocusable(cancel);

        int total = 0;
        Table itemTable = new Table(new int[]{42, 9, 3, 9});
        itemTable.setHeaders(new String[]{"Item Name", "Unit Cost", "Qty", "Total"});
        for (DBTransEntry entry : entries) {
            Price p = entry.getPrice();
            int q = entry.getQuantity();
            int t = p.value() * q;
            String itemName = entry.getItem().get().getName();
            if (itemName.length() > 42) itemName = itemName.substring(0, 39) + "...";
            itemTable.addRow(new String[]{itemName, p.toString(), "" + q, new Price(t).toString()});
            total += t;
        }
        itemTable.setPosition(7, 5);
        components.add(itemTable);
        itemTable.setHandler(() -> {
            if (itemTable.getSelected() >= 0) {
                DBTransEntry entry = entries.get(itemTable.getSelected());
                sm.setScreen(new ShowProduct(sm, null, entry.getItem().get(), false));
            }
        });
    }
}
