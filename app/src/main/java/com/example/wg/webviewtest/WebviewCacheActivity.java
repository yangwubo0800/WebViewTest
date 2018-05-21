package com.example.wg.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.File;

public class WebviewCacheActivity extends AppCompatActivity {

    private String  TAG = this.getClass().getName();
    private WebView mWebviewCache;
    private String baiduUrl = "https://www.baidu.com/";
    private String localFile = "file:///android_asset/index.html";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_cache);

        mWebviewCache = findViewById(R.id.webviewCache);
        mProgressBar = findViewById(R.id.progressBar);

        WebSettings webSettings = mWebviewCache.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置缓存模式，无网络时依然可以打开已经打开过的网页
        if (Utils.isNetworkAvailable(this)) {
            Log.d(TAG,"=====network available");
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            Log.d(TAG,"=====network not available");
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);

        mWebviewCache.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"=====shouldOverrideUrlLoading url=" + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                Log.d(TAG,"=====onPageFinished title=" + title);
            }

        });

        //设置加载过程 进度条
        mWebviewCache.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress ==  100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Log.d(TAG,"======onProgressChanged newProgress=" + newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
            });

        mWebviewCache.loadUrl(baiduUrl);

    }


    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"=====onDestroy");

        // TODO: 清理缓存, 删除相关缓存目录下的文件
        Utils.clearCacheFile(this);

        // TODO: 调用常规接口清理缓存
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        //webview 自身接口清理记录
        mWebviewCache.clearCache(true);
        mWebviewCache.clearFormData();
        mWebviewCache.clearHistory();


        //其实还是无法回收内存
        mWebviewCache.removeAllViews();
        mWebviewCache.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键处理
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebviewCache.canGoBack()) {
            //Utils.clearCacheFile(this);
            Log.d(TAG,"====test no network cache, when call back key");
            mWebviewCache.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
