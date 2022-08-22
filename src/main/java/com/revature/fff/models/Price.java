package com.revature.fff.models;

import java.text.NumberFormat;
import java.util.Locale;

public class Price {
    int price;
    static final Locale locale = new Locale("en", "US");
    static final NumberFormat nf = NumberFormat.getInstance(locale);

    public Price (int price) {
        this.price = price;
    }
    
    public int value() { return price; }

    @Override
    public String toString() {
        return "$" + nf.format(price / 100);
    }
}
