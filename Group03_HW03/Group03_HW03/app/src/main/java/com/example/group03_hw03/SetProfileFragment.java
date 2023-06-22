//a. Assignment HW03
//b. SetProfileFragment.java
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SetProfileFragment extends Fragment {
    Button setWeightBtn, cancelBtn;
    RadioGroup genderRadioGroup;
    String gender;
    EditText enterWeightEt;
    int weight;
    IFragmentNavigation iFragmentNavigation;

    public SetProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Set Weight/Gender");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_profile, container, false);
        gender="NS";
        setWeightBtn=view.findViewById(R.id.setWeightBtn);
        cancelBtn=view.findViewById(R.id.cancelBtn);
        genderRadioGroup=view.findViewById(R.id.genderRadioGroup);
        enterWeightEt=view.findViewById(R.id.enterWeightEt);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IFragmentNavigation)
            iFragmentNavigation=(IFragmentNavigation) context;
        else
            throw new RuntimeException(context.toString()+"hasn't implemented IFragmentNavigation Interface");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Set Weight/Gender");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderRb = view.findViewById(genderRadioGroup.getCheckedRadioButtonId());
                if(genderRb!=null)
                    gender=genderRb.getText().toString();
            }
        });

        setWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send Profile to MainActivity
                Profile profile=setTheWeight();
                if(profile!=null)
                    iFragmentNavigation.getProfileFromFragment(profile);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iFragmentNavigation.getProfileFromFragment(null);
            }
        });

    }

    private Profile setTheWeight() {
        Profile profile=null;
        String weightEntered = enterWeightEt.getText().toString();
        if(weightEntered.length()==0){
            Toast.makeText(getContext(),"Weight is a mandatory field, Please enter",Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(weightEntered)==0)
            Toast.makeText(getContext(),"Weight can't be 0",Toast.LENGTH_SHORT).show();
        else if(gender.equals("NS")){
            Toast.makeText(getContext(),"Gender is a mandatory field, Please select",Toast.LENGTH_SHORT).show();
        }else{
            weight=Integer.parseInt(weightEntered);
            profile=new Profile(weight,gender);
        }
        return profile;
    }
}