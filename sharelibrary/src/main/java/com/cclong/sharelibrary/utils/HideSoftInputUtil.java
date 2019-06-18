package com.cclong.sharelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 隐藏软键盘工具类
 */
public class HideSoftInputUtil {
    public static void hideKeyboard(Activity context) throws Exception {
        /*隐藏软键盘*/
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }
        if (inputMethodManager.isActive()) {
            View currentFocus = context.getCurrentFocus();
            if (currentFocus == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftInput(final View view) {
        if (view != null && view.getContext() != null) {
            final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }
            view.requestFocus();
            view.postDelayed(new Runnable() {
                public void run() {
                    imm.showSoftInput(view, 2);
                }
            }, 200L);
        }
    }

    public static void hideSoftInput(View view) {
        if (view != null && view.getContext() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
