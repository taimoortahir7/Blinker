package com.example.tamoor.blinker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class BroadcastService extends Service {

    private PhoneListener phoneStateListener;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
        phoneStateListener = new PhoneListener(context);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        Toast.makeText(context, "Service started!", Toast.LENGTH_SHORT).show();

        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
