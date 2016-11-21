package com.systemcare.systemcare;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.robotpajamas.blueteeth.BlueteethDevice;
import com.robotpajamas.blueteeth.BlueteethManager;
import com.robotpajamas.blueteeth.listeners.OnScanCompletedListener;

import java.util.List;

public class ReaderBeltService extends Service {
    public ReaderBeltService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BlueteethManager.with(ReaderBeltService.this).scanForPeripherals(5000, new OnScanCompletedListener() {
            @Override
            public void call(List<BlueteethDevice> blueteethDevices) {
                for(BlueteethDevice device : blueteethDevices) {
                    if(!TextUtils.isEmpty(device.getBluetoothDevice().getName())) {
                        Log.d("BLUETEETH",
                            String.format("%s - %s", device.getName(), device.getMacAddress()));
                    }
                }
            }
        });
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class SyncDeviceTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            BlueteethManager.with(ReaderBeltService.this).scanForPeripherals(5000, new OnScanCompletedListener() {
                @Override
                public void call(List<BlueteethDevice> blueteethDevices) {
                    for(BlueteethDevice device : blueteethDevices) {
                        if(!TextUtils.isEmpty(device.getBluetoothDevice().getName())) {
                            Log.d("BLUETEETH",
                                    String.format("%s - %s", device.getName(), device.getMacAddress()));
                        }
                    }
                }
            });
            return null;
        }
    }

}
