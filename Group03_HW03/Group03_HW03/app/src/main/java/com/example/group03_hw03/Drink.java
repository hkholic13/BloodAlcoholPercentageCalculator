//a. Assignment HW03
//b. Drink.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import java.io.Serializable;

public class Drink implements Serializable {
    private int alcoholPercentage;
    private int drinkSize;
    private String timestamp;

    public Drink(int alcoholPercentage, int drinkSize, String timestamp) {
        this.alcoholPercentage = alcoholPercentage;
        this.drinkSize = drinkSize;
        this.timestamp=timestamp;
    }

    public int getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(int alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public int getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(int drinkSize) {
        this.drinkSize = drinkSize;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
