package com.technotalkative.wearablelistviewdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Bundle dataMapBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        Log.v("MainActivity L", "create");

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    public void btnSimpleClick(View view){
        Intent intent = new Intent(this, SimpleListActivity.class);
        startActivity(intent);
    }

    public void btnAdvancedClick(View view){
        Intent listIntent = new Intent(MainActivity.this, AdvancedListActivity.class);
        listIntent.putExtra("datamap", dataMapBundle);
        startActivity(listIntent);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("MainActivity L", "Broadcast message received");
            Bundle data = intent.getBundleExtra("datamap");
            // Display local data in UI
            if (data != null) {
                dataMapBundle = data;
                findViewById(R.id.btnAdvanced).setVisibility(View.VISIBLE);
                Log.v("MainActivity L", "Map received");
            }
        }
    }

}
