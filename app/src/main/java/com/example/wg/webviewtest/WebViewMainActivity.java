package com.example.wg.webviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class WebViewMainActivity extends Activity {

    private  final String TAG = this.getClass().getName();
    private WebView mWebView;
    private LinearLayout mLayout;
    private String urlBaidu = "https://www.baidu.com/";
    private String videoUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4";
    private String localFile = "file:///android_asset/index.html";
    private String BaiduPic = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fr=&hs=0&xthttps=111111&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E6%A8%B1%E8%8A%B1&oq=%E6%A8%B1%E8%8A%B1&rsp=-1";
    private long start;
    private long end;
    private boolean firstBootFlag = false;
    private long pageStart;
    private long pageEnd;
    //内存分配测试，静态
    //private static int[][] memory = new int[10000][1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"=====onCreate");
        start = System.currentTimeMillis();
        firstBootFlag  = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity_main);

        //使用静态布局的webview
//        mWebView = (WebView) findViewById(R.id.wv);

        //混淆保留行号测试，制造空指针，查看log信息是否有对应信息
//        TextView proguard = null;
//        proguard.setText("proguard test");


        //使用动态创建的webview, 添加到静态布局layout中，便于进行网上说的释放
        mWebView = new WebView(getApplicationContext());
        mLayout = findViewById(R.id.webview_layout);
        //默认new出来的布局参数为null
        //ViewGroup.LayoutParams webviewLP = mWebView.getLayoutParams();
        //Log.d(TAG,"=====webview layout params " + webviewLP.toString());

        //set webview layout params
        LinearLayout.LayoutParams webviewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //add webview use layout params
        mLayout.addView(mWebView, webviewParams);
        ViewGroup.LayoutParams webviewLP = mWebView.getLayoutParams();
        if (null != webviewLP) {
            Log.d(TAG,"=====webview layout params " + webviewLP.toString());
        } else {
            Log.d(TAG,"=====webview layout params is null");
        }

        //settings
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBlockNetworkImage(false);
        //webSettings.setPluginState(WebSettings.PluginState.ON);
        //使用适配网页大小需要网页端代码兼容
        //webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //开启DOM Storage API
        webSettings.setDomStorageEnabled(true);
        //开启database
        webSettings.setDatabaseEnabled(true);

        // TODO:  以下设置路径方法已经没有作用，在手机上调试，不会按照指定路径生成缓存
        //设置缓存路径
//        String sdDir;
//        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if(isSDcardExist) {
//            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        } else {
//            sdDir = Environment.getRootDirectory().getAbsolutePath();
//        }
//        String cacheDir = sdDir +"/webviewtest/cache/";
//        Log.d(TAG,"=====webview cache dir =" + cacheDir);
//        webSettings.setDatabasePath(cacheDir);
//        webSettings.setAppCachePath(cacheDir);
//
        //开启app 缓存
        webSettings.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //SET WebView Client
        mWebView.setWebViewClient( new WebViewClient(){
            // when click a link from webview, the request maybe pass to browser, so need to override it.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"=====shouldOverrideUrlLoading url=" + url);
                //view.loadUrl(url);
                //return false; //to avoid redirection lead to can not back
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pageStart = System.currentTimeMillis();
                Log.d(TAG,"=====onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //long pageFinish = System.currentTimeMillis();
                pageEnd = System.currentTimeMillis();
                Log.d(TAG,"=====onPageFinished page load spent time "+ (pageEnd-pageStart) + "ms");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                           SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d(TAG,"=====onReceivedSslError error="+error.toString());
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG,"=====onReceivedError error="+error.toString());
            }

            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.d(TAG,"=====onReceivedHttpError errorResponse="+errorResponse.toString());
            }
        });

        //load url resource
        mWebView.loadUrl(localFile);
        // TODO: to check the multi process use js call natvie method.
        //add js api
        mWebView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void clickOnAndroid(){
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"=====webview call take photo");
                        takePhoto("testimg" + Math.random()*1000+1 + ".jpg");
                    }
                });
            }

            // TODO: can go no add API for java script use
        }, "index");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"=====onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        start = System.currentTimeMillis();
        Log.d(TAG,"=====onStart");
        //测试临时内存分配 30M
//        int[][] test = new int[10000][1000];
//        test[0][0] = 1;
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
        // TODO; 调用父类destroy 和 移除webview的顺序哪个在前面？
        super.onDestroy();
        Log.d(TAG,"=====onDestroy");

//        //清理缓存
//        clearWebviewCache(WebViewMainActivity.this, mWebView);
//
        //移除webview，然而实际过程中返回到native界面后，内存并不会释放很多
        //猜测webview的内存主要消耗在 native C层代码和，回收不了，只能杀死进程。
        mLayout.removeView(mWebView);
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;

        Log.d(TAG,"=====onDestroy before kill process");
        //use multi process to manage webview and kill itself when destroy webview
        android.os.Process.killProcess(android.os.Process.myPid());
        Log.d(TAG,"=====onDestroy after kill process");
//        //seconde way System.exit(0);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //when the webview fill the url many pages, back should be step by step.
        if(keyCode==KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            Log.d(TAG,"=====onKeyDown canGoBack");
            mWebView.goBack();
            return true;
        }
        Log.d(TAG,"=====onKeyDown");
        return super.onKeyDown(keyCode, event);
    }




    //------------------Todo: need to move to util class-----
    public void takePhoto(String filename) {
        Log.d(TAG,"=====takePhoto");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");
        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
        //确定相片保存路径
        String targetDir = sdDir + "/" + "webview_camera";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileFullName = targetDir + "/" + filename;
        System.out.println("----taking photo fileFullName: " + fileFullName);
        //初始化并调用摄像头
        intent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileFullName)));
        startActivityForResult(intent, 1);
    }


    private void clearWebviewCache(Context context, WebView webView) {

        //清空cookie
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        webView.clearCache(true);
        webView.clearHistory();
        //webView.clearFormData();
        boolean hasFormData = WebViewDatabase.getInstance(context).hasFormData();
        Log.d(TAG, "=====hasFormData="+hasFormData);
        if (hasFormData) {
            WebViewDatabase.getInstance(context).clearFormData();
        }
        Log.d(TAG,"=====clearWebviewCache");

    }
}
