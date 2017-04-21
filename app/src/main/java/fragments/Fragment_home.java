package fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.apsoft.scfb.efafootball.MyEventBus;
import com.apsoft.scfb.efafootball.R;

import org.greenrobot.eventbus.EventBus;

import contants.contants;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cnm on 2016/11/5.
 */

public class Fragment_home extends Fragment {
    private  WebView homeWeb;
    private ImageView  share_btn;
    ValueCallback<Uri> valueCallback;
    private long exitTime = 0;
    // 需要加载的网页URL地址
    private String url=
            "http://120.76.206.174:8080/efafootball-web/home.html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        homeWeb=(WebView)view.findViewById(R.id.home_web);
        share_btn=(ImageView)view.findViewById(R.id.share_btn);
        initWebView();  //初始化webview
        //添加点击事件
//        share_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String curren_url=homeWeb.getUrl();
//                if(curren_url.equals(contants.MOMENT_PAGE)){
//                    homeWeb.loadUrl(contants.MOMENT_PAGE_NEW);
//                }else if (curren_url.equals(contants.HOME_PAGE)){
//                    homeWeb.loadUrl(contants.MOMENT_PAGE);
//                }
//            }
//        });
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
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
            // Android > 4.1.1 调用这个方法
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                valueCallback = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, null), contants.FILE_CHOOSE);
            }
            // 3.0 + 调用这个方法
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){
                valueCallback = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "完成操作需要使用"),contants.FILE_CHOOSE);
            }

            // Android < 3.0 调用这个方法
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                valueCallback= uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "完成操作需要使用"),contants.FILE_CHOOSE);

            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(
                        getActivity())
                        .setTitle("温馨提示")
                        .setMessage(message)
                        .setPositiveButton("确认",
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                });
                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
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
        //写domstorage缓存
        webSettings.setDomStorageEnabled(true);
        //JavaScript中调用Android原生方法
        webSettings.setJavaScriptEnabled(true);
        homeWeb.addJavascriptInterface(new JavaScriptinterface(getActivity()),
                "android");
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
                            return true;
                        }
                        else if ((System.currentTimeMillis() - exitTime) > 2000) {
                            Toast.makeText(getActivity(), "再按一次退出程序",
                                    Toast.LENGTH_SHORT).show();
                            exitTime = System.currentTimeMillis();
                        } else {
                            getActivity().finish();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public class JavaScriptinterface {
        Context context;
        public JavaScriptinterface(Context c) {
            context= c;
        }
        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void JoinTeam() {
            //添加Android代码
            EventBus.getDefault().post(new MyEventBus(contants.JOIN_TEAM));
        }
        @JavascriptInterface
        public void MatchSignUp() {
            //添加Android代码
            EventBus.getDefault().post(new MyEventBus(contants.MATCH_SIGN_UP));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == contants.FILE_CHOOSE) {
            if (valueCallback == null)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            valueCallback.onReceiveValue(result);
            valueCallback = null;
        }
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

