package com.apsoft.scfb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

/**
 * Created by 晴识明月 on 2016/12/22.
 */

public class Welcome extends Activity {
    private Handler handler=new Handler();
    private  MyCountdownTimer countdowntimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        final SharedPreferences sharedpreferences=getSharedPreferences("test",Context.MODE_PRIVATE);
        countdowntimer = new MyCountdownTimer(2000, 1000);
        countdowntimer.start();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                /**
                 * Show Login Activity.
                 */
                Intent intent = new Intent();
                intent.setClass(Welcome.this, HomeMainActivity.class);
                startActivity(intent);
                /**
                 * Commit change: change state to 'true'.
                 */
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.putBoolean("hasVisited", true);
                edit.commit();
                finish();
            }
        }, 3000);
    }
    /*
    * Rewrite 'CountDownTimer' method.
    *
    * @param millisInFuture
    *            倒计时总数，单位为毫秒。
    * @param countDownInterval
    *            每隔多久调用onTick一次
    * @author DaiZhenWei
    *
    */
    protected class MyCountdownTimer extends CountDownTimer {

        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //countdown.setText("Close(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            //countdown.setText("Turning");
        }

    }
}
