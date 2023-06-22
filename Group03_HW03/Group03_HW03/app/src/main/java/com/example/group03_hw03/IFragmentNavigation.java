//a. Assignment Homework03
//b. IFragmentNavigation.java
//c.Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import java.util.ArrayList;

public interface IFragmentNavigation {
    void sendToSetProfile();
    void getProfileFromFragment(Profile profile);
    void sendToAddDrink();
    void addNewDrink(Drink drink);
    void passDrinkList(ArrayList<Drink> drinksList);
    void sendListBackToMain(ArrayList<Drink> drinkList);
}
