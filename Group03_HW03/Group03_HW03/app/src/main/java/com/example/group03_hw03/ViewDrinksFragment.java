//a. Assignment HW03
//b. ViewDrinksFragment.java
//c. Group - 03
//1) Harika Priya Prathipati
//2) Himanshi Khatri
package com.example.group03_hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDrinksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "drinkList";

    TextView viewDrinkTv, timeStampTv;
    ArrayList<Drink> drinkList;
    Button prevBtn, trashBtn, nextBtn,closeBtn;
    int position,drinkListSize;
    IFragmentNavigation iFragmentNavigation;


    public ViewDrinksFragment() {
        // Required empty public constructor
    }

    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinkList) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, drinkList);
        fragment.setArguments(args);
        return fragment;
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
        try {
            if (getArguments() != null) {
                drinkList = (ArrayList<Drink>) getArguments().getSerializable(ARG_PARAM1);
            }
        }catch (Exception e){}
        getActivity().setTitle("View Drinks");
        position=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_drinks, container, false);
        viewDrinkTv= view.findViewById(R.id.viewDrinkTv);
        timeStampTv= view.findViewById(R.id.timeStampTv);
        prevBtn= view.findViewById(R.id.prevBtn);
        trashBtn= view.findViewById(R.id.trashBtn);
        nextBtn= view.findViewById(R.id.nextBtn);
        closeBtn= view.findViewById(R.id.closeBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkListSize=drinkList.size();
                if(position<=0)
                    position=drinkListSize-1;
                else
                    position--;
                position=position%drinkListSize;
                setTheDrinkForView(position,drinkList);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkListSize=drinkList.size();
                position++;
                position=position%drinkListSize;
                setTheDrinkForView(position,drinkList);
            }
        });

        trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinkList.size()==1){
                    //go To Main Activity
                    drinkList.remove(position);
                    iFragmentNavigation.sendListBackToMain(drinkList);
                }
                else{
                    drinkList.remove(position);
                    if(position==0)
                        position=drinkList.size()-1;
                    else
                        position--;
                    setTheDrinkForView(position,drinkList);
                }
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iFragmentNavigation.sendListBackToMain(drinkList);
            }
        });

        setTheDrinkForView(position,drinkList);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("View Drinks");
    }

    private void setTheDrinkForView(int position, ArrayList<Drink> drinkList) {
        Drink ad=drinkList.get(position);
        viewDrinkTv.setText("Drink "+(position+1)+" out of "+drinkList.size()+"\n\n"+ad.getDrinkSize()+"oz"+"\n"+ad.getAlcoholPercentage()+"% Alcohol");
        timeStampTv.setText(ad.getTimestamp());
    }
}