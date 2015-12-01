package com.beijing.hekl.hekl.base;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.hekl.hekl.R;
import com.socks.library.KLog;

/**
 * Created by HEKL on 15/12/1.
 */
public class BaseFragmentActivity extends Activity {
    public ImageView titleLeftBtn;
    public Button titleRightBtn;
    public Button titleRightBtn2;
    public TextView titleTextV;
    public RelativeLayout title;

    public void initTitle() {
        titleLeftBtn = (ImageView) findViewById(R.id.title_back);
        titleRightBtn = (Button) findViewById(R.id.title_right);
        titleTextV = (TextView) findViewById(R.id.title_lable);
        titleRightBtn2 = (Button) findViewById(R.id.title_right2);
        title = (RelativeLayout) findViewById(R.id.title_root);
    }

    public final void setBackBtn(String text, View.OnClickListener listener) {
        titleLeftBtn.setBackgroundDrawable(null);
//		titleLeftBtn.setText(text);
        titleLeftBtn.setOnClickListener(listener);
    }

    public final void setBlackColor(TextView textView) {
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.text_color_black));
        }
    }

    public final void setTitle(String txt) {
        titleTextV.setText(txt);
    }

    public final void setRight(String txt, View.OnClickListener listener) {
        if (titleRightBtn2 == null) return;
        titleRightBtn2.setText(txt);
        titleRightBtn2.setVisibility(View.VISIBLE);
        titleRightBtn2.setOnClickListener(listener);
    }


    public final void setRightBtnYellowBg(String text, View.OnClickListener listener) {
        if (titleRightBtn2 == null) return;
        titleRightBtn2.setVisibility(View.VISIBLE);
        titleRightBtn2.setText(text);
        titleRightBtn2.setOnClickListener(listener);
    }


    public final void setRightMore(View.OnClickListener listener, int drawableId) {
        titleRightBtn2.setBackgroundResource(drawableId);
        titleRightBtn2.setVisibility(View.VISIBLE);
        titleRightBtn2.setOnClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_green));
//			window.setNavigationBarColor(getResources().getColor(R.color.man_color));
        }
        KLog.d("CLASS_NAME", getClass().toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//		AppManager.getInstance().finishActivity(this);
    }
}
