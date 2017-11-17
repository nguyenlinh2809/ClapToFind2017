package com.example.acer.claptofind2017;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static String permissionNameMicrophone = Manifest.permission.RECORD_AUDIO;
    boolean isStart = false;
    ImageButton imbtnToggle;
    TextView tvNotification;
    ShareReferencesManager shareReferencesManager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imbtnToggle = view.findViewById(R.id.btnToggle);
        tvNotification = view.findViewById(R.id.tvNotification);
        getShareReferenceSettings();
        addEvents();
        return view;
    }

    private void getShareReferenceSettings() {
        shareReferencesManager = new ShareReferencesManager(getActivity());
        isStart = shareReferencesManager.getStartStatus();
        if(isStart){
            imbtnToggle.setImageResource(R.drawable.button_on);
        }else {
            imbtnToggle.setImageResource(R.drawable.button_off);
        }
    }

    private void addEvents() {
        imbtnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = checkPermission(permissionNameMicrophone);
                if(check){
                    if(!isStart){
                        imbtnToggle.setImageResource(R.drawable.button_on);
                        isStart = true;
                        Intent intent = new Intent(getActivity(), ClapService.class);
                        getActivity().startService(intent);
                        tvNotification.setText("Service is started!");
                        tvNotification.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }else{
                        imbtnToggle.setImageResource(R.drawable.button_off);
                        isStart = false;
                        Intent intent = new Intent(getActivity(), ClapService.class);
                        getActivity().stopService(intent);
                        tvNotification.setText("Service is stopped!");
                        tvNotification.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }else{
                    requestPermissions(new String[]{permissionNameMicrophone}, MainActivity.PERMISSION_CODE);
                }

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        shareReferencesManager = new ShareReferencesManager(getActivity());
        shareReferencesManager.saveStartStatus(isStart);
    }

    public boolean checkPermission(String permissionName){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(permissionName) != PackageManager.PERMISSION_GRANTED){
                return false;
            }else return true;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((requestCode == MainActivity.PERMISSION_CODE) && (grantResults.length > 0)){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Replace by textview
                Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                isStart = true;
                imbtnToggle.setImageResource(R.drawable.button_on);
                Intent intent = new Intent(getActivity(), ClapService.class);
                getActivity().startService(intent);
                tvNotification.setText("Service is started!");
                tvNotification.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }else{
                Toast.makeText(getActivity(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                imbtnToggle.setImageResource(R.drawable.button_off);
                isStart = false;
                tvNotification.setText("Please grant Microphone permission to use this app");
                tvNotification.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }
}
