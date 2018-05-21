package com.example.wg.webviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by WG on 2018/5/12.
 */

public class NativeMainActivity extends Activity{

    private static final String TAG = "NativeMainActivity";
    private String url1 = "http://www.sinaimg.cn/qc/photo_auto/photo/84/35/39698435/39698435_140.jpg";
    private long start;
    private long end;
    private boolean firstBootFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"=====onCreate");
        start = System.currentTimeMillis();
        firstBootFlag  = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_activity_main);

        ImageView ivPicassoResult1 = (ImageView)findViewById(R.id.iv_picasso_result1);

        // 普通加载图片
        Picasso.with(NativeMainActivity.this)
                .load(url1)
                .into(ivPicassoResult1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        start = System.currentTimeMillis();
        Log.d(TAG,"=====onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"=====onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"=====onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"=====onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"=====onDestroy");
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
