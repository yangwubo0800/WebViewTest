package com.example.wg.webviewtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class PicassoListViewActivity extends Activity {

    private  final String TAG = this.getClass().getName();
    private long start;
    private long end;
    private boolean firstBootFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"=====onCreate");
        start = System.currentTimeMillis();
        firstBootFlag  = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picasso_listview);
        // 初始化listview
        ListView picassoListView = (ListView)findViewById(R.id.lv_picasso);
        //设置adapter
        picassoListView.setAdapter(new PicassoListViewAdapter(PicassoListViewActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        start = System.currentTimeMillis();
        Log.d(TAG,"=====onStart");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            end = System.currentTimeMillis();
            Log.d(TAG,"=====onWindowFocusChanged hasFocus="+hasFocus);
            if (firstBootFlag) {
                Log.d(TAG,"===== first boot load UI spent time "+(end-start)+" ms");
            } else {
                Log.d(TAG,"===== reboot load UI spent time "+(end-start)+" ms");
            }
            // just onCreate  can make it be true.
            firstBootFlag  = false;
        } else {
            Log.d(TAG,"=====onWindowFocusChanged hasFocus= false");
        }
    }
}
