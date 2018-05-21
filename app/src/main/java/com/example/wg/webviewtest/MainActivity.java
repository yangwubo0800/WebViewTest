package com.example.wg.webviewtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Set;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private long start;
    private long end;
    private boolean firstBootFlag = false;


    private boolean isStartedFromLauncher() {
        Intent intent = getIntent();
        String action = intent.getAction();
        int flags = intent.getFlags();
        Set<String> categories = intent.getCategories();
        return null != categories
                && categories.contains(Intent.CATEGORY_LAUNCHER)
                && Intent.ACTION_MAIN.equals(action)
                && (flags & Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED) != 0;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(TAG,"=====onCreate");

        start = System.currentTimeMillis();
        firstBootFlag  = true;
        super.onCreate(savedInstanceState);
        Log.d(TAG,"=====General Intent="+getIntent().toString());
        //解决第一次安卓后，从系统安装中直接打开APP,进入应用后，按HOME键退出到后台，重新再次从桌面进入，应用重启问题
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            Log.d(TAG,"=====getIntent="+getIntent().toString());
            finish();
            return;
        }


        setContentView(R.layout.activity_main);
        Button nativeLoad = (Button)findViewById(R.id.natvie_load);
        nativeLoad.setOnClickListener(myListener);
        Button webviewLoad = (Button)findViewById(R.id.webview_load);
        webviewLoad.setOnClickListener(myListener);
        Button test = (Button)findViewById(R.id.native_listview);
        test.setOnClickListener(myListener);
        Button webviewCache = (Button)findViewById(R.id.webview_cache);
        webviewCache.setOnClickListener(myListener);
    }


    //监听器设置的三种方式：1、使用类定义，2、使用接口定义，3、匿名
    private View.OnClickListener myListener = new View.OnClickListener(){
        public void onClick(View v) {
            Intent it = new Intent();
            switch (v.getId()) {
                case R.id.natvie_load:
                    it.setClass(MainActivity.this, NativeMainActivity.class);
                    break;
                case R.id.webview_load:
                    it.setClass(MainActivity.this, WebViewMainActivity.class);
                    break;
                case R.id.native_listview:
                    it.setClass(MainActivity.this, PicassoListViewActivity.class);
                    break;
                case R.id.webview_cache:
                    it.setClass(MainActivity.this, WebviewCacheActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(it);
        }
    };



    protected void onRestart() {
       super.onRestart();
       Log.d(TAG,"=====onRestart");
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
        }
    }


}
