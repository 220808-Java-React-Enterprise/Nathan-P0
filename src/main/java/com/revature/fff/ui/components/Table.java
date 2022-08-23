package com.revature.fff.ui.components;

import com.revature.fff.ui.Console;
import com.revature.fff.ui.IButton;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Table extends Component {
    private int[] widths;
    private int[] splits;
    private String[] headers;
    private ArrayList<String[]> rows = new ArrayList<>();
    private int selected = -1;
    private IButton rowHandler;
    
    public Table(int[] widths) {
        this.widths = new int[widths.length];
        headers = new String[widths.length];
        splits = new int[widths.length];
        System.arraycopy(widths, 0, this.widths, 0, widths.length);
        int split = 2;
        for (int i = 0; i < widths.length; i++) {
            splits[i] = split = split + widths[i] + 1;
        }
        setSize(4, splits[splits.length-1]+1);
    }
    
    public Table setHandler(IButton handler) {
        rowHandler = handler;
        return this;
    }
    
    public void setHeaders(String[] headers) {
        if (this.headers.length == headers.length)
            System.arraycopy(headers, 0, this.headers, 0, headers.length);
    }
    
    public void addRow(String[] row) {
        if (row.length == headers.length) {
            rows.add(row.clone());
            setSize(getHeight()+1, getWidth());
        }
    }
    
    public int getSelected() { return selected; }
    
    @Override
    public void draw(@NotNull Console c) {
        c.setMarginsRelative(bounds);
        int row = 0;
        c.setPosition(row, 0);
        c.print("┌─");
        for (int w : widths) {
            c.print("┬");
            for (int i = 0; i < w; i++) c.print("─");
        }
        c.print("┐");
        c.setPosition(++row, 0);
        drawRow(c, headers, row);
        c.setPosition(++row, 0);
        c.print("├─");
        for (int w : widths) {
            c.print("┼");
            for (int i = 0; i < w; i++) c.print("─");
        }
        c.print("┤");
        for (String[] d : rows) {
            c.setPosition(++row, 0);
            drawRow(c, d, row);
        }
        c.setPosition(++row, 0);
        c.print("└─");
        for (int w : widths) {
            c.print("┴");
            for (int i = 0; i < w; i++) c.print("─");
        }
        c.print("┘");
        if (selected >= 0) {
            c.setPosition(3 + selected, 1);
            c.print("*");
        }
        c.restoreMargins();
    }
    
    private void drawRow(Console c, String[] row, int r) {
        c.print("│ ");
        for (int i = 0; i < splits.length; i++) {
            c.print("│");
            c.print(row[i]);
            c.setPosition(r, splits[i]);
        }
        c.print("│");
    }

    @Override
    public void process(String command) {
        if (command.isEmpty() && rowHandler != null) rowHandler.execute();
        else for (char c : command.toCharArray()) {
            if (c == '^') {
                selected--;
                if (selected < 0) selected = rows.size() - 1;
            }
            else if (c == 'v')  {
                selected++;
                if (selected >= rows.size()) selected = 0;
            }
        }
    }
}
