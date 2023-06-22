//a. Assignment HW03
//b. MainActivity.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IFragmentNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BAC Calculator");
        inflateTheFragmentOnMainActivity(new BacCalculatorFragment(),0,"BacCalculator");
    }

    private void inflateTheFragmentOnMainActivity(Fragment fragmentToInflate, int flagForBackStack, String backStackTag) {
        if(flagForBackStack==0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,fragmentToInflate,backStackTag)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,fragmentToInflate,backStackTag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void sendToSetProfile() {
        inflateTheFragmentOnMainActivity(new SetProfileFragment(),1,"setProfile");
    }

    @Override
    public void getProfileFromFragment(Profile profile) {
        getSupportFragmentManager().popBackStack();
        if(profile!=null){
            BacCalculatorFragment bacCalculatorFragment= (BacCalculatorFragment) getSupportFragmentManager().findFragmentByTag("BacCalculator");
            if(bacCalculatorFragment!=null){
                bacCalculatorFragment.setUserProfile(profile);
            }else
                throw new RuntimeException("Failed to Set User Profile");
        }
    }

    @Override
    public void sendToAddDrink() {
        inflateTheFragmentOnMainActivity(new AddDrinkFragment(),1,"addDrink");
    }

    @Override
    public void addNewDrink(Drink drink) {
        if(drink!=null){
            BacCalculatorFragment bacCalculatorFragment= (BacCalculatorFragment) getSupportFragmentManager().findFragmentByTag("BacCalculator");
            if(bacCalculatorFragment!=null){
                bacCalculatorFragment.updateDrinkList(drink);
            }else
                throw new RuntimeException("Failed to Set User Profile");
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void passDrinkList(ArrayList<Drink> drinksList) {
        inflateTheFragmentOnMainActivity(new ViewDrinksFragment().newInstance(drinksList),1,"viewDrinks");
    }

    @Override
    public void sendListBackToMain(ArrayList<Drink> drinkList) {
        BacCalculatorFragment bacCalculatorFragment= (BacCalculatorFragment) getSupportFragmentManager().findFragmentByTag("BacCalculator");
        if(bacCalculatorFragment!=null){
            bacCalculatorFragment.updateAssignDrinkList(drinkList);
        }else
            throw new RuntimeException("Failed to Set User Profile");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        ViewDrinksFragment viewDrinksFragment = (ViewDrinksFragment) getSupportFragmentManager().findFragmentByTag("viewDrinks");
        if(viewDrinksFragment!=null){
            Toast.makeText(getApplicationContext(),"Back Button on View Drinks Fragment is not allowed, Use Close button..!!", Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressed();
        }
    }
}