package com.beijing.hekl.hekl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.hekl.hekl.Utils.AppUtils;
import com.beijing.hekl.hekl.base.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editText;
    private ViewStub stub;
    private ConnectionChangeReceiver mReceiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        NetStatService.
    }

    private void initView() {
        initTitle();
        titleLeftBtn.setVisibility(View.INVISIBLE);
        titleTextV.setText(R.string.app_name);
        titleRightBtn2.setText("RxJava");
//      titleRightBtn2.setVisibility(View.VISIBLE);
        titleRightBtn2.setOnClickListener(this);
        stub = (ViewStub) findViewById(R.id.vs_netWork);
        editText = (EditText) findViewById(R.id.main_edittext);
        stub.inflate();
        findViewById(R.id.vs_button).setOnClickListener(this);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String str = editText.getText().toString().trim();
                    if (str.length() > 0) {
                        Intent intent = new Intent(MainActivity.this, AtyHospitalList.class);
                        intent.putExtra("name", str);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();

    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vs_button:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
        }
    }

    /**
     * 系统广播监听
     */
    class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!AppUtils.isNetworkAvailable(context)) {
                stub.setVisibility(View.VISIBLE);
            } else {
                stub.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 注册网络监听
     */
    private void registerReceiver() {
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new ConnectionChangeReceiver();
        this.registerReceiver(mReceiver, filter);
    }

    /**
     * 反注册网络监听
     */
    private void unregisterReceiver() {
        this.unregisterReceiver(mReceiver);
    }
}
