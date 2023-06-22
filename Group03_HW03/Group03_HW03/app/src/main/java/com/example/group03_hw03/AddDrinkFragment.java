//a. Assignment HW03
//b. IFragmentNavigation.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddDrinkFragment extends Fragment {
    RadioGroup drinkRadioGroup;
    int drinkSize,alcoholPercentage;
    SeekBar seekBar;
    TextView alcoholPercentageTv;
    Button cancelBtn, addDrinkBtn;
    IFragmentNavigation iFragmentNavigation;

    public AddDrinkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Toast.makeText(getContext(),"OnAttach",Toast.LENGTH_SHORT).show();
        if(context instanceof IFragmentNavigation)
            iFragmentNavigation=(IFragmentNavigation) context;
        else
            throw new RuntimeException(context.toString()+"hasn't implemented IFragmentNavigation Interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Add Drink");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drink, container, false);
        drinkSize=0;
        alcoholPercentage=0;
        drinkRadioGroup=view.findViewById(R.id.drinksRadioGroup);
        alcoholPercentageTv=view.findViewById(R.id.alcoholPercentage);
        cancelBtn=view.findViewById(R.id.cancelBtn);
        addDrinkBtn=view.findViewById(R.id.addDrinkBtn);
        seekBar=view.findViewById(R.id.seekbar);
        seekBar.setMax(30);
        alcoholPercentageTv.setText("0%");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drinkRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String drinkSizeStr;
                RadioButton drinkRb= view.findViewById(drinkRadioGroup.getCheckedRadioButtonId());
                if(drinkRb!=null){
                    drinkSizeStr=drinkRb.getText().toString();
                    if(drinkSizeStr.equals("1 oz"))
                        drinkSize=1;
                    if(drinkSizeStr.equals("5 oz"))
                        drinkSize=5;
                    if(drinkSizeStr.equals("12 oz"))
                        drinkSize=12;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alcoholPercentage=i;
                alcoholPercentageTv.setText(i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel
                iFragmentNavigation.addNewDrink(null);
            }
        });

        addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinkSize==0)
                    Toast.makeText(getContext(),"Please set the drink Size",Toast.LENGTH_SHORT).show();
                else if(alcoholPercentage==0)
                    Toast.makeText(getContext(),"Alcohol Percentage can't be 0%",Toast.LENGTH_SHORT).show();
                else{
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.US);
                    String dateStr= dateFormat.format(new Date());
                    iFragmentNavigation.addNewDrink(new Drink(alcoholPercentage,drinkSize,"Added "+dateStr));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Drink");
    }
}