package com.revature.fff.ui.screens;

import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.TransactionService;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Label;
import com.revature.fff.ui.components.Table;

import java.util.List;

public class ShowHistory extends Screen {
    public ShowHistory(ScreenManager sm) {
        super(sm);
        DBUser user = UserService.getActiveUser();
        if (user == null) {
            sm.setScreen(new Login(sm));
            return;
        }
        Label title = new Label("Past Purchases");
        title.setPosition(2, 5);
        add(title);

        Table history = new Table(new int[]{11, 8, 23});
        history.setHeaders(new String[]{"Transaction", "Location", "Date"});
        List<DBTransaction> entries = TransactionService.getTransactionHistory();
        for (DBTransaction entry : entries) {
            history.addRow(new String[]{entry.getId().toString().substring(0, 8),
                    String.format("%04d", entry.getLocation().get().getNumber()),
                    entry.getModified().toString()});
        }
        history.setPosition(6, 5);
        add(history);
        addFocusable(history);
        history.setHandler(() -> {
            if (history.getSelected() >= 0) 
                sm.setScreen(new ShowInvoice(sm, entries.get(history.getSelected()), false));
        });
    }
}
