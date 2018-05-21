package com.example.wg.webviewtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;

public class Utils {
    private static final String TAG = Utils.class.getClass().getName();


    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        try {
            // TODO: 这种递归处理是否会导致删除不干净，中途异常退出
            if (null == file) {
                Log.d(TAG,"=====deleteFile file is null");
                return;
            }

            if (file.exists()) {
                //文件直接删除
                if (file.isFile()) {
                    file.delete();
                    //目录再次遍历，考虑目录层级多的情况是否会栈溢出
                } else if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        //递归删除
                        deleteFile(files[i]);
                    }
                }
                //不是文件，又不是目录?
                file.delete();
            } else {
                Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWithFile(dir);
    }

    public static void deleteDirWithFile(File dir) {

        try {
            if (dir == null || !dir.exists() || !dir.isDirectory()) {
                Log.d(TAG,"=====deleteDirWithFile return");
                return;
            }
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    Log.d(TAG,"#####file  path is " + file.getPath());
                    file.delete(); // 删除所有文件
                }
                else if (file.isDirectory()) {
                    deleteDirWithFile(file); // 递规的方式删除文件夹
                }
            }
            dir.delete();// 删除目录本身
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 删除指定目录下的缓存文件
     * @param context
     */
    public static void clearCacheFile(Context context) {

        // TODO: 清理缓存, 删除相关缓存目录下的文件
        String cachePath = context.getCacheDir().getPath();
        Utils.deleteDir(cachePath);
        Log.d(TAG,"=====cachePath=" + cachePath);

        String webviewCachePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            //高版本的目录是否如此需要测试，是否可以使用模拟器
            webviewCachePath = context.getDataDir().getPath() + "/app_webview";
        } else {
            webviewCachePath = "/data/data/" + context.getPackageName() + "/app_webview";
        }
        Utils.deleteDir(webviewCachePath);
        Log.d(TAG,"=====webviewCachePath=" + webviewCachePath);

        //delete  code cache  dir
        String codeCachePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            codeCachePath = context.getCodeCacheDir().getPath();
        } else {
            //低版本的目录是否如此需要测试，是否可以使用模拟器
            codeCachePath = "/data/data/" + context.getPackageName() + "/code_cache";
        }
        Utils.deleteDir(codeCachePath);
        Log.d(TAG,"=====codeCachePath=" + codeCachePath);

    }


    /**
     * 检测是否联网
     * @param activity
     * @return
     */
    public static boolean isNetworkAvailable(Context activity) {
        //得到应用上下文
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）  notificationManager /alarmManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
