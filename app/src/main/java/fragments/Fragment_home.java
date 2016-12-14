package fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.cnm.efafootball.R;

/**
 * Created by cnm on 2016/11/5.
 */

public class Fragment_home extends Fragment {
    private  WebView homeWeb;
    // 需要加载的网页URL地址
    private String url=
            "http://120.76.206.174:8080/efafootball-web/home.html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        homeWeb=(WebView)view.findViewById(R.id.home_web);
        initWebView();  //初始化webview
        return view;
    }
    private void initWebView() {
        homeWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //系统默认会打开系统浏览器去打开网页，为了要显示在自己的webview中必须设置这个属性
                view.loadUrl(url);

                return super.shouldOverrideUrlLoading(view, url);
            }

            //加载开始时调用

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            // 加载结束时调用

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


        });

        homeWeb.setWebChromeClient(new WebChromeClient() {
            //加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }


            @Override
            public void onRequestFocus(WebView view) {
                super.onRequestFocus(view);
            }

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

        });

        homeWeb.loadUrl(url);
        // 得到setting
        WebSettings webSettings=homeWeb.getSettings();
        //设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持的语言类型
        webSettings.setDefaultTextEncodingName("UTF-8");
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        webSettings.setDomStorageEnabled(true);
        //设置可以加载图片资源
        webSettings.setLoadsImagesAutomatically(true);
        homeWeb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK ) {
                        //这里处理返回键事件
                        if (homeWeb.canGoBack()){
                            homeWeb.goBack();
                            Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清除记录
        homeWeb.clearCache(true);
        homeWeb.clearHistory();
        homeWeb.clearFormData();
        homeWeb.destroy();
    }
}

