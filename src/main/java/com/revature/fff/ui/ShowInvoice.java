package com.revature.fff.ui;

import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBTransEntry;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.Price;
import com.revature.fff.services.TransactionService;

import java.util.List;

public class ShowInvoice extends Screen {
    public ShowInvoice(ScreenManager sm, DBTransaction transaction, boolean buy) {
        super(sm);
        if (!buy) {
            Label thanks = new Label("Thank you for your purchase!");
            thanks.setPosition(2, 5);
            components.add(thanks);
        }
        
        int total = 0;
        Table itemTable = new Table(new int[]{42, 9, 3, 9});
        itemTable.setHeaders(new String[]{"Item Name", "Unit Cost", "Qty", "Total"});
        List<DBTransEntry> entries = TransactionService.getEntriesForTransaction(transaction);
        for (DBTransEntry entry : entries) {
            Price p = entry.getPrice();
            int q = entry.getQuantity();
            int t = p.value() * q;
            String itemName = entry.getItem().get().getName();
            if (itemName.length() > 42) itemName = itemName.substring(0, 39) + "...";
            itemTable.addRow(new String[]{itemName, p.toString(), "" + q, new Price(t).toString()});
            total += t;
        }
        itemTable.setPosition(6, 5);
        components.add(itemTable);
        addFocusable(itemTable);
        itemTable.setHandler(() -> {
            if (itemTable.getSelected() >= 0) {
                DBTransEntry entry = entries.get(itemTable.getSelected());
                sm.setScreen(new ShowProduct(sm, null, entry.getItem().get(), false));
            }
        });
        
        Label customer = new Label("Customer: " + transaction.getCustomer().get().getUsername());
        customer.setPosition(3, 5);
        components.add(customer);
        
        Label id = new Label("Invoice: " + transaction.getId().toString().substring(0, 8));
        id.setPosition(3, 40);
        components.add(id);
        
        Label location = new Label("Location: " + String.format("%04d", transaction.getLocation().get().getNumber()));
        location.setPosition(4, 5);
        components.add(location);
        
        Label price = new Label("Total: " + new Price(total));
        price.setPosition(4, 40);
        components.add(price);
    }
}
