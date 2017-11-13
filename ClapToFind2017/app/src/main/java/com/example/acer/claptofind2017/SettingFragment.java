package com.example.acer.claptofind2017;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    TextView tvPickRingtone, tvRingtoneResult;
    TextView tvPickSensity, tvResultSensity;
    CheckBox cbRingtone, cbFlash, cbVibrate;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvPickRingtone = view.findViewById(R.id.tvPickRingtone);
        tvRingtoneResult = view.findViewById(R.id.tvResultRingtone);
        tvPickSensity = view.findViewById(R.id.tvPickSensity);
        tvResultSensity = view.findViewById(R.id.tvResultSensity);
        cbRingtone = view.findViewById(R.id.cbSound);
        cbFlash = view.findViewById(R.id.cbFlash);
        cbVibrate = view.findViewById(R.id.cbVibrate);
        getShareReferenceSettings();
        addEvents();
        return view;
    }

    private void getShareReferenceSettings() {
        ShareReferencesManager shareReferencesManager = new ShareReferencesManager(getActivity());
        cbRingtone.setChecked(shareReferencesManager.getRingtoneStatus());
        cbFlash.setChecked(shareReferencesManager.getFlashStatus());
        cbVibrate.setChecked(shareReferencesManager.getVibrateStatus());
        tvRingtoneResult.setText(shareReferencesManager.getRingtoneName());
        tvResultSensity.setText(shareReferencesManager.getSensityStatus());
    }

    @Override
    public void onPause() {
        super.onPause();
        ShareReferencesManager shareReferencesManager = new ShareReferencesManager(getActivity());
        shareReferencesManager.saveSettings(cbRingtone.isChecked(), cbFlash.isChecked(), cbVibrate.isChecked(), tvRingtoneResult.getText().toString(), tvResultSensity.getText().toString());
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
        final ListView lvRingtone = (ListView)dialog.findViewById(R.id.lvRingtone);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_dialog_ok);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);


        final ArrayList<String> listSong = new ArrayList<>();
        listSong.add("RingTone 1");
        listSong.add("RingTone 2");
        listSong.add("RingTone 3");

        final ArrayAdapter<String> adapterSong = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, listSong);
        lvRingtone.setAdapter(adapterSong);
        int position = listSong.indexOf(tvRingtoneResult.getText().toString());
        lvRingtone.setItemChecked(position, true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvRingtoneResult.setText(listSong.get(lvRingtone.getCheckedItemPosition()));
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
