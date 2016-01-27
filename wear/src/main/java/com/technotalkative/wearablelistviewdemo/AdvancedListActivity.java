package com.technotalkative.wearablelistviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.wearable.DataMap;
import com.technotalkative.wearablelistviewdemo.model.DataObject;

import java.util.ArrayList;

public class AdvancedListActivity extends Activity implements WearableListView.ClickListener  {

    private WearableListView mListView;
    private MyListAdapter mAdapter;

    private float mDefaultCircleRadius;
    private float mSelectedCircleRadius;

    private ArrayList<DataObject> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        //Get bunlde with dataMap.
        Bundle bundle = getIntent().getBundleExtra("datamap");
        if (bundle != null) {
            DataMap dataMap = DataMap.fromBundle(bundle);
            itemList = getDataItemListList(dataMap);
        }

        mDefaultCircleRadius = getResources().getDimension(R.dimen.default_settings_circle_radius);
        mSelectedCircleRadius = getResources().getDimension(R.dimen.selected_settings_circle_radius);
        mAdapter = new MyListAdapter();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView1);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(AdvancedListActivity.this);
            }
        });
    }

    /**
     * Convert DataMap list into ArrayList of Objects.
     * @param dMap
     * @return
     */
    private ArrayList<DataObject> getDataItemListList(DataMap dMap) {
        ArrayList<DataObject> llist = new ArrayList<>();
        ArrayList<DataMap> dmapList = dMap.getDataMapArrayList("DATA_LIST");
        if (dmapList != null && dmapList.size() > 0) {
            for (DataMap dataMap : dmapList) {
                DataObject item = new DataObject(dataMap);
                llist.add(item);
            }
        }
        return llist;
    }

    private static ArrayList<Integer> listItems;
    static {
        listItems = new ArrayList<Integer>();
        listItems.add(R.drawable.ic_action_attach);
        listItems.add(R.drawable.ic_action_call);
        listItems.add(R.drawable.ic_action_copy);
        listItems.add(R.drawable.ic_action_cut);
        listItems.add(R.drawable.ic_action_delete);
        listItems.add(R.drawable.ic_action_done);
        listItems.add(R.drawable.ic_action_edit);
        listItems.add(R.drawable.ic_action_locate);
        listItems.add(R.drawable.ic_action_mail);
        listItems.add(R.drawable.ic_action_mail_add);
        listItems.add(R.drawable.ic_action_microphone);
        listItems.add(R.drawable.ic_action_photo);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(this, String.format("You selected item #%s", viewHolder.getPosition()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {
        Toast.makeText(this, "You tapped Top empty area", Toast.LENGTH_SHORT).show();
    }

    private void openWebUrl() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cnn.com"));
        startActivity(browserIntent);
    }

    /**
     * Method to open map directions on your handheld.
     */
    private void openMapDirections() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Golden+Gate+Park,+San Francisco+USA");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public class MyListAdapter extends WearableListView.Adapter {

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(new MyItemView(AdvancedListActivity.this));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            MyItemView itemView = (MyItemView) viewHolder.itemView;

            TextView txtView = (TextView) itemView.findViewById(R.id.text);
            txtView.setText(itemList.get(i).getName());

            Integer resourceId = listItems.get(i);
            CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
            imgView.setImageResource(resourceId);
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }
    }

    private final class MyItemView extends FrameLayout implements WearableListView.OnCenterProximityListener {

        final CircledImageView imgView;
        final TextView txtView;
        private float mScale;
        private final int mFadedCircleColor;
        private final int mChosenCircleColor;

        public MyItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.row_advanced_item_layout, this);
            imgView = (CircledImageView) findViewById(R.id.image);
            txtView = (TextView) findViewById(R.id.text);
            mFadedCircleColor = getResources().getColor(android.R.color.darker_gray);
            mChosenCircleColor = getResources().getColor(android.R.color.holo_blue_dark);
        }

        @Override
        public void onCenterPosition(boolean b) {
            //Animation example to be ran when the view becomes the centered one
            imgView.animate().scaleX(1f).scaleY(1f).alpha(1);
            txtView.animate().scaleX(1f).scaleY(1f).alpha(1);
        }

        @Override
        public void onNonCenterPosition(boolean b) {
            //Animation example to be ran when the view is not the centered one anymore
            imgView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
            txtView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        }
    }
}
