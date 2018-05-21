# WebViewTest
webview功能测试（对比H5和native界面加载区别，清理webview cache方法）


遇到的问题：
1、网页端重定向导致返回键失效问题：
最简单的方法就是在shouldOverrideUrlLoading方法里面返回false,让WebView自身去处理，就可以避免这种问题的发生。

 2、是否需要重新 shouldOverideUrlLoading 方法
主要看时webview自己处理url还是交由浏览器处理

3、内存查看方法：
使用dumpsys命令
adb shell dumpsys meminfo com.example.wg.webviewtest > meminfo.txt
使用top命令
top | grep com.example.wg.webviewtest

查看任务栈中是否有多个实例
ACTIVITY MANAGER ACTIVITIES (dumpsys activity activities)

Running activities (most recent first):

4、页面显示问题
setUseWideViewPort 将这个熟悉设置为true之后，将导致本地网页只显示一部分界面。
webSettings.setUseWideViewPort(true);
设置是否支持viewport属性。设置为true后，如果html中没有明码定义viewport，webview则用默认的viewport。

5、Uncaught TypeError: window.demo.clickOnAndroid is not a function", source: file:///android_asset/index.html
// 被JS调用的方法必须加入@JavascriptInterface注解
@JavascriptInterface
如果有多个方法需要绑定，则需要将多个方法都写入对象中。

7、使用手机系统第一次安装apk之后直接打开，进入应用二级菜单，将应用退到后台，然后再次从桌面进入应用，出现应用重启重新回到主菜单的问题。
厂商bug:
http://www.cnblogs.com/yangwubo/p/9056907.html
