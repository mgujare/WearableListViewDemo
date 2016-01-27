package com.technotalkative.wearablelistviewdemo;

/**
 * Created by mgujare on 1/26/16.
 */

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class WearListenerService extends WearableListenerService {

    private static final String TAG =  WearListenerService.class.getSimpleName();
    public static final String DATA_PATH = "/data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        DataMap dataMap;
        for (DataEvent event : dataEvents) {
            Log.v("WearListenerService L", "data event occurred");
            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.v("WearListenerService L", "listener data change");
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(DATA_PATH)) {
                    dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    if (dataMap != null) {
                        Log.v("WearListenerService L", "Map received");
                        Intent messageIntent = new Intent();
                        messageIntent.setAction(Intent.ACTION_SEND);
                        messageIntent.putExtra("datamap", dataMap.toBundle());
                        LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                    }
                }
            }
        }
    }

}
