package com.cclong.sharelibrary.pops;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.cclong.sharelibrary.ICCLongPop;
import com.cclong.sharelibrary.R;
import com.cclong.sharelibrary.enmus.SharePlatform;
import com.cclong.sharelibrary.utils.HideSoftInputUtil;

import java.util.List;


/**
 * @author ly
 * @brief 基础弹窗
 * @date 2018/4/28.
 */
public class BaseSharePopupwindow extends PopupWindow {
    private Activity activity;
    private ValueAnimator valueAnimator;
    private boolean isFinish;
    protected ICCLongPop mICCLongPop;
    protected List<SharePlatform> mPlatforms;

    public void setPlatforms(List<SharePlatform> platforms) {
        this.mPlatforms = platforms;
    }

    public void setICCLongPop(ICCLongPop iCCLongPop) {
        this.mICCLongPop = iCCLongPop;
    }

    protected void init(Activity activity) {
        this.activity = activity;
        try {
            HideSoftInputUtil.hideKeyboard(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true); //获取焦点
        setTouchable(true);
        setOutsideTouchable(false); //设置popupwindow外部可点击
        ColorDrawable dw = new ColorDrawable(0x7f000000); //实例化一个ColorDrawable颜色为半透明
        setBackgroundDrawable(dw); //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//        int identifier = activity.getApplication().getResources().getIdentifier("popwindow_anim_style", "style", activity.getPackageName());
        setAnimationStyle(R.style.popwindow_anim_style);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                hideBackground();
                if (mICCLongPop != null && !isFinish) {
                    isFinish = false;
                    mICCLongPop.onPopCallback(null);
                }
            }
        });
    }

    public void dismiss(boolean isFinish) {
        this.isFinish = isFinish;
        super.dismiss();
    }

    public void showPopupWindow() {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        showBackground();
    }

    protected void showBackground() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.end();
        }
        valueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                backgroundAlpha(value);
            }
        });
        valueAnimator.start();
    }

    public void hideBackground() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.end();
        }
        valueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                backgroundAlpha(value);
            }
        });
        valueAnimator.start();
    }

    protected void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
