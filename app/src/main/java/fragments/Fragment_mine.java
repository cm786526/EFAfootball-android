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

import com.example.cnm.efafootball.R;

import contants.contants;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cnm on 2016/11/5.
 */

public class Fragment_mine extends Fragment {
    private WebView mineWeb;
    private ImageView go_back;
    ValueCallback<Uri> valueCallback;
    private long exitTime = 0;
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
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
            // Android > 4.1.1 �����������
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                valueCallback = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, null), contants.FILE_CHOOSE);
            }
            // 3.0 + �����������
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){
                valueCallback = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "��ɲ�����Ҫʹ��"),contants.FILE_CHOOSE);
            }

            // Android < 3.0 �����������
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                valueCallback= uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(
                Intent.createChooser(intent, "��ɲ�����Ҫʹ��"),contants.FILE_CHOOSE);

            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(
                        getActivity())
                        .setTitle("��ܰ��ʾ")
                        .setMessage(message)
                        .setPositiveButton("ȷ��",
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
        mineWeb.addJavascriptInterface(new JavaScriptinterface(getActivity()),
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
                        else if ((System.currentTimeMillis() - exitTime) > 2000) {
                            Toast.makeText(getActivity(), "�ٰ�һ���˳�����",
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
         * ��js����ʱ�õ��ķ�������js��ֱ�ӵ��õ�
         */
        @JavascriptInterface
        public void showToast(String ssss) {
            //���Android����
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
        //�����¼
        mineWeb.clearCache(true);
        mineWeb.clearHistory();
        mineWeb.clearFormData();
        mineWeb.destroy();
    }
}
