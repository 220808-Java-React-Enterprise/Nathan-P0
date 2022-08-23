package com.revature.fff.ui.components;

import com.revature.fff.ui.Console;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public abstract class Container extends Component {
    HashSet<Component> components = new HashSet<>();

    public Container add(Component c) {
        components.add(c);
        return this;
    }

    @Override
    public void draw(@NotNull Console con) {
        con.setMarginsRelative(bounds);
        for (Component c: components) c.draw(con);
        con.restoreMargins();
    }
}
