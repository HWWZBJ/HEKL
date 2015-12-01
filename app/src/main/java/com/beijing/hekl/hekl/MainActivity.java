package com.beijing.hekl.hekl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.hekl.hekl.Utils.AppUtils;
import com.beijing.hekl.hekl.Utils.ToastUtil;
import com.beijing.hekl.hekl.base.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initTitle();
        titleLeftBtn.setVisibility(View.INVISIBLE);
        titleTextV.setText(R.string.app_name);
        titleRightBtn2.setText("RxJava");
//        titleRightBtn2.setVisibility(View.VISIBLE);
        titleRightBtn2.setOnClickListener(this);
        editText= (EditText) findViewById(R.id.main_edittext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    // 先隐藏键盘
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String str=editText.getText().toString().trim();
                    if (str.length()>0) {
                        Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
                        intent.putExtra("name", str);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AppUtils.isNetworkAvailable(this)) {
            ToastUtil.showShort(this, "当前网络不可用,请去设置网络连接!");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
