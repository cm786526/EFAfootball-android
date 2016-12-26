package fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.cnm.efafootball.R;

/**
 * Created by cnm on 2016/11/5.
 */

public class Fragment_mine extends Fragment {
    private WebView mineWeb;
    private ImageView go_back;
    // ��Ҫ���ص���ҳURL��ַ
    private String url=
            "http://120.76.206.174:8080/efafootball-web/mine.html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        mineWeb=(WebView)view.findViewById(R.id.mine_web);
        go_back=(ImageView)view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mineWeb.canGoBack()){
                    mineWeb.goBack();
                }
            }
        });
        initWebView();
        return view;
    }
    private void initWebView() {
        mineWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //ϵͳĬ�ϻ��ϵͳ�����ȥ����ҳ��Ϊ��Ҫ��ʾ���Լ���webview�б��������������
                view.loadUrl(url);

                return super.shouldOverrideUrlLoading(view, url);
            }

            //���ؿ�ʼʱ����

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            // ���ؽ���ʱ����

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


        });

        mineWeb.setWebChromeClient(new WebChromeClient() {
            //���ؽ���
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

        mineWeb.loadUrl(url);
        // �õ�setting
        WebSettings webSettings=mineWeb.getSettings();
        //����֧��Javascript
        webSettings.setJavaScriptEnabled(true);
        //֧�ֵ���������
        webSettings.setDefaultTextEncodingName("UTF-8");
        //���ÿ��Է����ļ�
        webSettings.setAllowFileAccess(true);
        //дdomstorage����
        webSettings.setDomStorageEnabled(true);
        //JavaScript�е���Androidԭ������
        webSettings.setJavaScriptEnabled(true);
        mineWeb.addJavascriptInterface(new JavaScriptinterface(getContext()),
                "android");
        mineWeb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK ) {
                        //���ﴦ���ؼ��¼�
                        if (mineWeb.canGoBack()){
                            mineWeb.goBack();
                            return true;
                        }
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
         * ��js����ʱ�õ��ķ�������js��ֱ�ӵ��õ�
         */
        @JavascriptInterface
        public void showToast(String ssss) {
            //���Android����
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //�����¼
        mineWeb.clearCache(true);
        mineWeb.clearHistory();
        mineWeb.clearFormData();
        mineWeb.destroy();
    }
}
