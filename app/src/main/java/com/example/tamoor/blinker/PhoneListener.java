package com.example.tamoor.blinker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class PhoneListener extends PhoneStateListener {
    private Context context;
    public static String getincomno;
    private callInterface callInterface;
//    private static int speed = 0;
    private Blinker blinker;


    public PhoneListener(Context c) {
        Log.i("CallRecorder", "PhoneListener constructor");
        context = c;
    }

    public void onCallStateChanged (int state, String incomingNumber)
    {
        blinker = new Blinker(context);
        callInterface = blinker;

        if(!TextUtils.isEmpty(incomingNumber)){
            // here for Outgoing number make null to get incoming number
//            CallBroadcastReceiver.numberToCall = null;
            getincomno = incomingNumber;
        }

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Toast.makeText(context, "idle", Toast.LENGTH_SHORT).show();
                callInterface.endBlinker();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Toast.makeText(context, "Ringing!", Toast.LENGTH_SHORT).show();
                callInterface.startBlinker();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Toast.makeText(context, "offhook", Toast.LENGTH_SHORT).show();
                callInterface.endBlinker();
                break;
        }
    }

//    @Override
//    public void setSpeed(int s) {
//        speed = s;
//    }

    public interface callInterface {
        void endBlinker();
        void startBlinker();
    }

}
