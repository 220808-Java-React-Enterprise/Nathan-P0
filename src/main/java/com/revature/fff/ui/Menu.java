package com.revature.fff.ui;

import java.util.ArrayList;

public class Menu extends Screen {
    private ArrayList<MenuItem> items = new ArrayList<>();

    public Menu(ScreenManager sm) {
        super(sm);
    }

    public Menu addItem(MenuItem item) {
        components.add(item);
        items.add(item);
        return this;
    }

    public void layout() {
        super.layout();
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            item.setIndex(i);
            item.setTop(i + 3);
            item.setLeft(10);
        }
    }

    public void processInput(String input) {
        getActive().process("");
    }
}
