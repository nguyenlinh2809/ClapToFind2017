package com.example.acer.claptofind2017;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

    private static final int RINGTONE_PICKER_REQUEST_CODE = 933;
    TextView tvPickRingtone, tvRingtoneResult, tvNotificationFlash;
    TextView tvPickSensity, tvResultSensity;
    CheckBox cbRingtone, cbFlash, cbVibrate;

    public static String permissionNameCamera = Manifest.permission.CAMERA;

    ShareReferencesManager shareReferencesManager;

    Snackbar snackbar;

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

        getShareReferenceSettings();
        addEvents();
        return view;
    }

    private void getShareReferenceSettings() {

        cbRingtone.setChecked(shareReferencesManager.getRingtoneStatus());
        cbFlash.setChecked(shareReferencesManager.getFlashStatus());
        cbVibrate.setChecked(shareReferencesManager.getVibrateStatus());
        if(shareReferencesManager.getRingtoneURI().equals(String.valueOf(Settings.System.DEFAULT_RINGTONE_URI))){
            tvRingtoneResult.setText("Default Ringtone");
        }else {
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(shareReferencesManager.getRingtoneURI()));
            tvRingtoneResult.setText(ringtone.getTitle(getActivity()));
        }

        tvResultSensity.setText(shareReferencesManager.getSensityStatus());
    }

    @Override
    public void onPause() {
        super.onPause();
        shareReferencesManager.saveSettings(cbRingtone.isChecked(), cbFlash.isChecked(), cbVibrate.isChecked(), tvResultSensity.getText().toString());

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

                boolean hasFlash = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                if(!hasFlash){
                    Toast.makeText(getActivity(), "This device does not support Flash!", Toast.LENGTH_LONG).show();
                    cbFlash.setChecked(false);
                    cbFlash.setEnabled(false);
                    shareReferencesManager.saveFlash(false);
                }else {
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
                Vibration vibration = new Vibration(getActivity());
                if(!vibration.hasVibration()){
                    Toast.makeText(getActivity(), "This device does not support Vibration!", Toast.LENGTH_SHORT).show();
                    cbVibrate.setChecked(false);
                    cbVibrate.setEnabled(false);
                    shareReferencesManager.saveVibrate(false);
                }else{
                    shareReferencesManager.saveVibrate(b);
                }

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
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Please select your ringtone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(shareReferencesManager.getRingtoneURI()));
        startActivityForResult(intent, RINGTONE_PICKER_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RINGTONE_PICKER_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            shareReferencesManager.saveRingtoneURI(String.valueOf(uri));
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
            String title = ringtone.getTitle(getActivity());
            if (title.contains("Default ringtone")){
                tvRingtoneResult.setText("Default ringtone");
            }else {
                tvRingtoneResult.setText(title);
            }

        }
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
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionNameCamera)){
                    Toast.makeText(getActivity(), "Please grant Camera permission to use Flash function", Toast.LENGTH_LONG).show();
                    tvNotificationFlash.setText("Please grant Camera permission to use Flash function");
                    tvNotificationFlash.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }else{
                    tvNotificationFlash.setText("");
                    tvNotificationFlash.setBackgroundColor(getResources().getColor(android.R.color.white));
                    Toast.makeText(getActivity(), "Permission denied, please enanle to use this function!", Toast.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(getView(), getResources().getString(R.string.Snacbar), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getResources().getString(R.string.Snacbar_message), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getActivity() == null){
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            getActivity().startActivity(intent);
                        }
                    });
                    snackbar.show();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(snackbar != null){
            snackbar.dismiss();
        }
    }
}
