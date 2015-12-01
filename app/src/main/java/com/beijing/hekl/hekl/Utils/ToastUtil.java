package com.beijing.hekl.hekl.Utils;

import android.content.Context;
import android.widget.Toast;

import com.beijing.hekl.hekl.app.Happlication;

/**
 * Created by HEKL on 15/12/1.
 * Used for 提示工具
 */
public class ToastUtil {

    private static final int TShortTime = 800;

    public ToastUtil() {
    }

    public static void onShow(Context context, int strid, int duration) {
        Toast toast = Toast.makeText(context, context.getString(strid), duration);
        toast.show();
    }

    public static void onShow(Context context, String str, int duration) {
        Toast toast = Toast.makeText(context, str, duration);
        toast.show();
    }

    /**
     * 最基本的Toast提示
     *
     * @param context 上下文
     * @param content 提示内容
     */
    public static void showBasicShortToast(Context context, String content) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 网络连接错误提示
     *
     * @param context 上下文
     */
    public static void showNetWorkErrorToast(Context context) {
        Toast toast = Toast.makeText(context, "网络错误,请稍后重试", Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showShort(Context context, int strid) {
        onShow(context, strid, TShortTime);
    }

    public static void showShort(Context context, String str) {
        onShow(context, str, TShortTime);
    }

    public static void showShort(String str) {
        showShort(Happlication.getApplication(), str);
    }

    public static void showLong(Context context, int strid) {
        onShow(context, strid, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, String content) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showStorageUninToast(Context context) {
//		showShort(context,R.string.sd_uninstall);
    }

    public static void showSDCardBusy() {
        showShort("内存卡暂无法使用");
    }

    public static void showCreateFail() {
        showShort("文件创建失败");
    }

    public static void showGetImageFail() {
        showShort("图片获取失败");
    }
}
