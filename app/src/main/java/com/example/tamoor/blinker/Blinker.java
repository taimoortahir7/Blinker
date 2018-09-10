package com.example.tamoor.blinker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class Blinker implements PhoneListener.callInterface {

    private Context context;
    boolean isFlashOn = true;
    static Camera camera = null;
    private Camera.Parameters params;
    private static boolean cameraService = false;
    private PhoneListener.callInterface callInterface;
    static boolean flashLightStatus = true;

    public Blinker(Context context) {
        this.context = context;
    }

    public void blink() {
        flashLightStatus = true;

        callInterface = this;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        } else {
            if(!cameraService) {
                initializeCamera();
            }
        }

        Thread t = new Thread() {
            public void run() {
                try {
                    while (flashLightStatus){
                        onFlashLight();
                        offFlashLight();
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void initializeCamera() {
            camera = Camera.open();
            isFlashOn = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            params = camera.getParameters();
            cameraService = true;
    }

    private void onFlashLight() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager cameraManager = null;
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

            String cameraId = null;
            try {
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            try {
                if (cameraManager != null) {
                    cameraManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
//            isFlashOn = false;
        } else {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
//            isFlashOn = false;
        }
    }

    private void offFlashLight() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager cameraManager = null;
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

            String cameraId = null;
            try {
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            try {
                if (cameraManager != null) {
                    cameraManager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
//            isFlashOn = true;
        } else {
            if(!cameraService) {
                initializeCamera();
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
//            isFlashOn = true;
        }
    }

    @Override
    public void endBlinker() {
        flashLightStatus = false;
    }

    @Override
    public void startBlinker() {
        blink();
    }

}
