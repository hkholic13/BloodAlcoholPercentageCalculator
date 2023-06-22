//a. Assignment HW03
//b. BacCalculatorFragment.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BacCalculatorFragment extends Fragment {
    Button setWeightBtn, resetBtn, addDrinkBtn, viewDrinkBtn;
    TextView setWeightTv, drinkCounterTv, bacLevelTv, resultTv;

    int drinkSize = 0;
    double bacLevel = 0;
    double rConstant = 0, aConstant = 0;
    DecimalFormat df;
    ArrayList<Drink> drinksList;
    Profile profile;
    IFragmentNavigation iFragmentNavigation;
    boolean newProfileFlag;

    public BacCalculatorFragment() {
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
        //Toast.makeText(getContext(),"OnCreate",Toast.LENGTH_SHORT).show();
        getActivity().setTitle("BAC Calculator");
        df = new DecimalFormat("0.000");
        bacLevel = 0; rConstant = 0; aConstant = 0; drinkSize = 0;
        drinksList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getContext(),"OnCreateView",Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bac_calculator, container, false);

        setWeightBtn=view.findViewById(R.id.setWeightNaviBtn);
        setWeightTv = view.findViewById(R.id.setWeight);
        drinkCounterTv = view.findViewById(R.id.drinkCounter);
        bacLevelTv = view.findViewById(R.id.bacLevel);
        resultTv = view.findViewById(R.id.result);
        resetBtn = view.findViewById(R.id.resetBtn);
        addDrinkBtn = view.findViewById(R.id.addDrinkNaviBtn);
        viewDrinkBtn= view.findViewById(R.id.viewDrinkBtn);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toast.makeText(getContext(),"OnViewCreated",Toast.LENGTH_SHORT).show();
        setWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send To SetProfile Fragment
                iFragmentNavigation.sendToSetProfile();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAll(1);
            }
        });

        addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GoTo AddDrink Fragment
                iFragmentNavigation.sendToAddDrink();
            }
        });
        viewDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinksList.size()==0)
                    Toast.makeText(getContext(),"User don't have any drink added yet..!!",Toast.LENGTH_SHORT).show();
                else{
                    //send to ViewDrinks Frgament
                    iFragmentNavigation.passDrinkList(drinksList);
                }
            }
        });

        if(profile==null)
            resetAll(1);
        else {
            addDrinkBtn.setEnabled(true);
            viewDrinkBtn.setEnabled(true);
            setWeightTv.setText(getString(R.string.setWeightStr) + getString(R.string.spaceHolder) + profile.getWeight() + " lbs (" + profile.getGender() + ")");
            if(newProfileFlag==true) {
                resetAll(0);
                newProfileFlag = false;
            }
            else
                calcBAC();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"OnResume",Toast.LENGTH_SHORT).show();
        getActivity().setTitle("BAC Calculator");
    }

    public void setUserProfile(Profile profilePara){
        this.profile=profilePara;
        newProfileFlag=true;
    }
    public void updateDrinkList(Drink drink){
        drinksList.add(drink);
    }
    public void updateAssignDrinkList(ArrayList<Drink> updatedListFromViewFragment){
        drinksList=updatedListFromViewFragment;
    }

    private void calcBAC() {
        aConstant = 0;
        rConstant = (profile.getGender().equals("Male")) ? 0.73 : 0.66;
        for (Drink ad : drinksList) {
            aConstant += (double) (ad.getDrinkSize() * ad.getAlcoholPercentage()) / 100;
        }
        bacLevel = (aConstant * 5.14) / (profile.getWeight() * rConstant);
        drinkCounterTv.setText(getString(R.string.drinkCounterStr) + getString(R.string.spaceHolder) + drinksList.size());
        bacLevelTv.setText(getString(R.string.bacLevelStr) + getString(R.string.spaceHolder) + df.format(bacLevel));
        if (bacLevel >= 0 && bacLevel <= 0.08) {
            resultTv.setText(getString(R.string.safeStatusStr));
            resultTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.safebg));
        } else if (bacLevel > 0.08 && bacLevel <= 0.2) {
            resultTv.setText(getString(R.string.warningStatusStr));
            resultTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.warnbg));
        } else if (bacLevel > 0.2) {
            resultTv.setText(getString(R.string.errorStatusStr));
            resultTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.errbg));
        }

        if (bacLevel >= 0.25) {
            addDrinkBtn.setEnabled(false);
            Toast.makeText(getContext(), "No more drinks for you.", Toast.LENGTH_LONG).show();
        }else{
            addDrinkBtn.setEnabled(true);
        }
    }
    private void resetAll(int flag) {
        bacLevel = 0;
        drinkSize = 0;
        drinksList.clear();
        if(flag==1){
            newProfileFlag=false;
            addDrinkBtn.setEnabled(false);
            viewDrinkBtn.setEnabled(false);
            setWeightTv.setText(getString(R.string.setWeightStr) + getString(R.string.spaceHolder) + getString(R.string.nAStr));
            profile=null;
        }
        drinkCounterTv.setText(getString(R.string.drinkCounterStr) + getString(R.string.spaceHolder) + drinksList.size());
        bacLevelTv.setText(getString(R.string.bacLevelStr) + getString(R.string.spaceHolder) + df.format(bacLevel));
        resultTv.setText(getString(R.string.safeStatusStr));
        resultTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.safebg));
    }
}