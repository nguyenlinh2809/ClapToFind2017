package com.example.acer.claptofind2017;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    TextView tvPickRingtone, tvRingtoneResult, tvNotificationFlash;
    TextView tvPickSensity, tvResultSensity;
    CheckBox cbRingtone, cbFlash, cbVibrate;

    int position = 0;
    ArrayList<String> listSong;
    public static String permissionNameCamera = Manifest.permission.CAMERA;

    ShareReferencesManager shareReferencesManager;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shareReferencesManager = new ShareReferencesManager(getActivity());
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvPickRingtone = view.findViewById(R.id.tvPickRingtone);
        tvRingtoneResult = view.findViewById(R.id.tvResultRingtone);
        tvPickSensity = view.findViewById(R.id.tvPickSensity);
        tvResultSensity = view.findViewById(R.id.tvResultSensity);
        cbRingtone = view.findViewById(R.id.cbSound);
        cbFlash = view.findViewById(R.id.cbFlash);
        cbVibrate = view.findViewById(R.id.cbVibrate);
        tvNotificationFlash = view.findViewById(R.id.tvNotificationFlash);

        listSong = new ArrayList<>();
        listSong.add("ek_villain_sad");
        listSong.add("miss_pooja");
        listSong.add("preman");
        listSong.add("ringtone");
        listSong.add("sumon");
        listSong.add("voice_bawa");
        listSong.add("in_the_end");
        listSong.add("numb");
        listSong.add("ultil_you");
        getShareReferenceSettings();
        addEvents();
        return view;
    }

    private void getShareReferenceSettings() {

        cbRingtone.setChecked(shareReferencesManager.getRingtoneStatus());
        cbFlash.setChecked(shareReferencesManager.getFlashStatus());
        cbVibrate.setChecked(shareReferencesManager.getVibrateStatus());
        tvRingtoneResult.setText(listSong.get(shareReferencesManager.getRingtonePosition()));
        tvResultSensity.setText(shareReferencesManager.getSensityStatus());
    }

    @Override
    public void onPause() {
        super.onPause();
        shareReferencesManager.saveSettings(cbRingtone.isChecked(), cbFlash.isChecked(), cbVibrate.isChecked(), tvResultSensity.getText().toString());
        Log.d("position", position+"");
    }

    private void addEvents() {
        tvPickRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRingtoneDialog();

            }
        });
        tvPickSensity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSensityDialog();
            }
        });
        cbFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    boolean check = checkPermission(permissionNameCamera);
                    if(check){
                        shareReferencesManager.saveFlash(b);
                    }else {
                        requestPermissions(new String[]{permissionNameCamera}, MainActivity.PERMISSION_CODE);
                    }
                }else{
                    shareReferencesManager.saveFlash(b);
                }

            }
        });
        cbRingtone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shareReferencesManager.saveRingtone(b);
            }
        });
        cbVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shareReferencesManager.saveVibrate(b);
            }
        });
    }

    private void showSensityDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_select_sensity_layout);
        final SeekBar sbSensity = dialog.findViewById(R.id.sbSensity);
        final TextView tvSensityProgress = dialog.findViewById(R.id.tvSensityProgress);
        Button btnSensityOk = dialog.findViewById(R.id.btn_sensity_ok);
        Button btnSensityCancel = dialog.findViewById(R.id.btn_sensity_cancel);

        int progress = Integer.parseInt(tvResultSensity.getText().toString());
        if(progress == 0){
            sbSensity.setProgress(40);
        }
        sbSensity.setProgress(progress);
        tvSensityProgress.setText(progress+" %");

        btnSensityCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSensityOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResultSensity.setText(sbSensity.getProgress() +"");
                shareReferencesManager.saveSensity(tvResultSensity.getText().toString());
                dialog.dismiss();
            }
        });
        sbSensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvSensityProgress.setText(i + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        dialog.show();
    }

    private void showRingtoneDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_select_ringtone_layout);
        final ListView lvRingtone = dialog.findViewById(R.id.lvRingtone);
        Button btnOk = dialog.findViewById(R.id.btn_dialog_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_cancel);

        final ArrayAdapter<String> adapterSong = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, listSong);
        lvRingtone.setAdapter(adapterSong);
        position = listSong.indexOf(tvRingtoneResult.getText().toString());
        lvRingtone.setItemChecked(position, true);
        lvRingtone.setSelection(position);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = lvRingtone.getCheckedItemPosition();
                shareReferencesManager.saveRingtonePosition(position);
                tvRingtoneResult.setText(listSong.get(position));
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public boolean checkPermission(String permissionName){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(permissionName) != PackageManager.PERMISSION_GRANTED){
                return false;
            }else return true;
        }else {
            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((requestCode == MainActivity.PERMISSION_CODE) && (grantResults.length > 0)){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                tvNotificationFlash.setText("");
                tvNotificationFlash.setBackgroundColor(getResources().getColor(android.R.color.background_light));
            }else{
                cbFlash.setChecked(false);
                tvNotificationFlash.setText("Please grant Camera permission to use Flash function");
                tvNotificationFlash.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }

}
