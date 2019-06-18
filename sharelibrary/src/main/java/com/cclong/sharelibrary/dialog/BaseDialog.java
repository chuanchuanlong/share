package com.cclong.sharelibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cclong.sharelibrary.R;


/**
 * @brief 基础对话框
 */
public class BaseDialog extends Dialog {
    private String mDismissByClicBg;

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private BaseDialog dialog;
        private String title;
        private String content;
        private String buttonLeftText;
        private String buttonRightText;
        private OnClickListener leftlistener;
        private OnClickListener rightlistener;
        private String mClickBgToHide;
        private OnClickBgToHideListener mHideListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * @brief Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = context.getResources().getString(title);
            return this;
        }

        public Builder setClickBgToHide(String clickBgToHide) {
            this.mClickBgToHide = clickBgToHide;
            return this;
        }

        public Builder setClicBgToHideListener(OnClickBgToHideListener hideListener) {
            this.mHideListener = hideListener;
            return this;
        }

        /**
         * @brief Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(int content) {
            this.content = context.getResources().getString(content);
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setLeftButton(String text, OnClickListener listener) {
            buttonLeftText = text;
            this.leftlistener = listener;
            return this;
        }

        public Builder setLeftButton(int textId, OnClickListener listener) {
            buttonLeftText = context.getResources().getString(textId);
            this.leftlistener = listener;
            return this;
        }

        public Builder setRightButton(String text, OnClickListener listener) {
            buttonRightText = text;
            this.rightlistener = listener;
            return this;
        }

        public Builder setRightButton(int textId, OnClickListener listener) {
            buttonRightText = context.getResources().getString(textId);
            this.rightlistener = listener;
            return this;
        }

        public BaseDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.share_dialog_base_layout, null);
            final TextView tvTitleName = (TextView) layout.findViewById(R.id.tv_title_name);
            TextView tvContent = (TextView) layout.findViewById(R.id.tv_content);
            TextView tvLeft = (TextView) layout.findViewById(R.id.tv_left);
            TextView tvRight = (TextView) layout.findViewById(R.id.tv_right);
            View vDivider = layout.findViewById(R.id.v_divider);
            //判断是否需要标题
            if (TextUtils.isEmpty(title)) {
                tvTitleName.setVisibility(View.GONE);
            } else {
                tvTitleName.setVisibility(View.VISIBLE);
                tvTitleName.setText(title);
            }
            // 判断是否要内容
            if (TextUtils.isEmpty(content)) {
                tvContent.setVisibility(View.GONE);
            } else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(content);
            }
            // 判断是否有分割线
            if (!TextUtils.isEmpty(buttonLeftText) && !TextUtils.isEmpty(buttonRightText)) {
                vDivider.setVisibility(View.VISIBLE);
            } else {
                vDivider.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(buttonLeftText)) {
                tvLeft.setVisibility(View.GONE);
            } else {
                tvLeft.setVisibility(View.VISIBLE);
                tvLeft.setText(buttonLeftText);
                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (leftlistener != null) {
                            dialog.mDismissByClicBg = "";
                            leftlistener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    }
                });
            }
            if (TextUtils.isEmpty(buttonRightText)) {
                tvRight.setVisibility(View.GONE);
            } else {
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText(buttonRightText);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightlistener != null) {
                            dialog.mDismissByClicBg = "";
                            rightlistener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    }
                });
            }
            dialog = new BaseDialog(context, R.style.base_dialog);
            Display display = dialog.getWindow().getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            final int screenHeight = (int) (display.getHeight() * 0.75f);
            int width = screenWidth - dp2px(context, 105);
            dialog.setCancelable(false);
            if ("1".equals(mClickBgToHide)) {
                dialog.setCanceledOnTouchOutside(true);
            } else {
                dialog.setCanceledOnTouchOutside(false);
            }
            if (screenHeight < screenWidth) {
                dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                dialog.addContentView(layout, new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            dialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if ("1".equals(dialog.mDismissByClicBg)) {
                        mHideListener.onClickToHide();
                    }
                }
            });
            return dialog;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View mView = getWindow().getDecorView().findViewById(R.id.ll_root);
        if (mView != null) {
            int location[] = new int[2];
            mView.getLocationOnScreen(location);
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (y < location[1] || y > location[1] + mView.getHeight() || x < location[0] || x > location[0] + mView.getWidth()) {
                mDismissByClicBg = "1";
            } else {
                mDismissByClicBg = "";
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * @brief 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnClickBgToHideListener {
        void onClickToHide();
    }
}
