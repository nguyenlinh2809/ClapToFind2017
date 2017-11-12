package com.example.acer.claptofind2017;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    boolean isStart = false;
    ImageButton imbtnToggle;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imbtnToggle = (ImageButton) view.findViewById(R.id.btnToggle);
        imbtnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isStart){
                    imbtnToggle.setImageResource(R.drawable.button_on);
                    isStart = true;
                }else{
                    imbtnToggle.setImageResource(R.drawable.button_off);
                    isStart = false;
                }
            }
        });
        return view;
    }

}
