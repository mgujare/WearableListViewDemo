package com.technotalkative.wearablelistviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.technotalkative.wearablelistviewdemo.model.DataObject;

import java.util.ArrayList;


public class MyActivity extends Activity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String DATA_LIST = "DATA_LIST";
    private static final String DATA_PATH = "/data";
    private ArrayList<DataMap> dataMapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        dataMapList = generateLocalDataMap(DataObject.getItemList());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void btnSendClick(View view) {
        new SendToDataLayerThread(DATA_PATH, "Mesg from handheld").start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private ArrayList<DataMap> generateLocalDataMap(ArrayList<DataObject> lList) {
        if (lList != null) {
            dataMapList = new ArrayList<DataMap>();
            for (DataObject ldata:lList) {
                DataObject mapData = new DataObject(ldata.getId(), ldata.getName());
                DataMap dataMap = mapData.putToDataMap(new DataMap());
                dataMapList.add(dataMap);
            }
        }
        return dataMapList;
    }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create(path);
            putDataMapReq.getDataMap().putDataMapArrayList(DATA_LIST, dataMapList);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            DataApi.DataItemResult pendingResult =
                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq).await();

            if (pendingResult.getStatus().isSuccess()) {
                Log.v("MyActivity", "DataMap:  sent successfully to data layer ");
            } else {
                // Log an error
                Log.v("MyActivity", "ERROR: failed to send DataMap to data layer");
            }

        }
    }
}
