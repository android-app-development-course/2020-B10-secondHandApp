package com.example.myapplication.ui.start_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.activity.MainActivity1;
import com.example.myapplication.R;

/**
 * APP启动的时候的3s加载页面
 */
public class StartUP extends AppCompatActivity implements View.OnClickListener {

    private TextView ad_time_tv;
    private int time=3;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (time <= 0) {
                enterToMain();
            } else {
                time = time - 1;
                ad_time_tv.setText("跳过" + time);
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }

    };

    private void showAd() {
        ad_time_tv.setText("跳过" + time);
        mHandler.sendEmptyMessageDelayed(0, 1000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        initView();
        showAd();
    }

    private void initView() {
        ad_time_tv = findViewById(R.id.ad_time_tv);
        ad_time_tv.setOnClickListener(this);
    }

    private void enterToMain() {
        Intent intent = new Intent(StartUP.this, MainActivity1.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ad_time_tv:
                time=0;
        }
    }
}
