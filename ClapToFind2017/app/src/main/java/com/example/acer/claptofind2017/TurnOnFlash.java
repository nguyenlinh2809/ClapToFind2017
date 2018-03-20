package com.example.acer.claptofind2017;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class TurnOnFlash {
    Camera mCamera;
    Camera.Parameters mParams;
    int delay = 100;
    boolean on = false;
    Context context;

    public TurnOnFlash(Context context){
        this.context = context;

        boolean hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(hasFlash){
            /*if (mCamera != null) {
                mCamera.release();
                mCamera.stopPreview();
                mCamera = null;
            }*/

            //add try catch
            try{
                mCamera = Camera.open();
                mParams = mCamera.getParameters();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            Toast.makeText(context, "This device does not support Flash Light!", Toast.LENGTH_SHORT).show();
            ShareReferencesManager shareReferencesManager = new ShareReferencesManager(context);
            shareReferencesManager.saveFlash(false);
            mCamera = null;
            return;
        }


    }
    public void blinkFlash(boolean end){
        for(int i=0; i<3; i++){
            toggleFlashLight();
            if(end){
                turnOff();
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void turnOn() {
        if(mCamera != null){
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParams);
            mCamera.startPreview();
            on = true;
        }else return;


       /* if (mCamera == null) {
            //mParams = mCamera.getParameters();

            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParams);
            mCamera.startPreview();
            on = true;
        }else return;*/

    }

    public void turnOff() {
        if(mCamera != null){
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParams);
            mCamera.stopPreview();
            on = false;
        }else return;



       /* if (mCamera != null) {
            //mParams = mCamera.getParameters();
            if (mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mParams);
                mCamera.stopPreview();
                on = false;
            }
        }else return;*/

    }
    public void m_turnOff() {
        if(mCamera != null){
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParams);
            mCamera.stopPreview();
            on = false;

            mCamera.stopPreview();
            mCamera.release();
            mParams = null;
            mCamera = null;
        }else return;

    }

    public void toggleFlashLight() {
        if (!on) {
            turnOn();
        } else {
            turnOff();
        }
    }

}
