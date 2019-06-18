package com.cclong.sharelibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.cclong.sharelibrary.R;
import com.cclong.sharelibrary.dialog.BaseDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 * Created by longshaojiang on 2018/4/12.
 */

public class PermisionManager {

    private static final int APP_SETTING_CODE = 10001;
    private static final String TAG = "PermisionManager";
    private static PermisionManager mInstance;
    private int mGrantedCount;

    public static PermisionManager instance() {
        if (mInstance == null) {
            mInstance = new PermisionManager();
        }
        return mInstance;
    }

    public void requestPermision(final Activity activity, final PermistionResult permistionResult, final String... permissions) {
        mGrantedCount = 0;
        RxPermissions rxpermissions = new RxPermissions((FragmentActivity) activity);
        rxpermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    mGrantedCount++;
                    if (mGrantedCount == permissions.length) {
                        permistionResult.onResult(true);
                    }
                } else if (permission.shouldShowRequestPermissionRationale) {

                } else {
                    requestAgain(activity, permission);
                }
            }
        });
    }

    public void requestPermision(final Fragment fragment, final PermistionResult permistionResult, final String... permissions) {
        mGrantedCount = 0;
        RxPermissions rxpermissions = new RxPermissions(fragment.getActivity());
        rxpermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    mGrantedCount++;
                    if (mGrantedCount == permissions.length) {
                        permistionResult.onResult(true);
                    }
                } else if (permission.shouldShowRequestPermissionRationale) {

                } else {
                    requestAgain(fragment, permission);
                }
            }
        });
    }

    private void requestAgain(final Fragment fragment, Permission permission) {
        try {
            int appName = fragment.getResources().getIdentifier("app_name", "string", fragment.getActivity().getPackageName());
            BaseDialog baseDialog = new BaseDialog.Builder(fragment.getActivity())
                    .setContent(String.format("%s需要%s权限，否则部分功能无法使用", fragment.getString(appName), getPermistionAlias(permission.name)))
                    .setTitle("")
                    .setLeftButton("暂不设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setRightButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SettingJumperUtils.startAppSettings(fragment, APP_SETTING_CODE);
                            dialog.dismiss();
                        }
                    })
                    .create();
            baseDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private void requestAgain(final Activity activity, Permission permission) {
        try {
            int appName = activity.getResources().getIdentifier("app_name", "string", activity.getPackageName());
            BaseDialog baseDialog = new BaseDialog.Builder(activity)
                    .setContent(String.format("%s需要%s权限，否则部分功能无法使用", activity.getString(appName), getPermistionAlias(permission.name)))
                    .setTitle("")
                    .setLeftButton("暂不设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setRightButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SettingJumperUtils.startAppSettings(activity, APP_SETTING_CODE);
                            dialog.dismiss();
                        }
                    })
                    .create();
            baseDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private String getPermistionAlias(String permisionName) {
        if (TextUtils.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, permisionName)
                || TextUtils.equals(Manifest.permission.READ_EXTERNAL_STORAGE, permisionName)
        ) {
            return "存储";
        } else if (TextUtils.equals(Manifest.permission.CAMERA, permisionName)) {
            return "拍照和录像";
        } else if (TextUtils.equals(Manifest.permission.CALL_PHONE, permisionName)
                || TextUtils.equals(Manifest.permission.READ_PHONE_STATE, permisionName)
        ) {
            return "电话";
        } else if (TextUtils.equals(Manifest.permission.ACCESS_COARSE_LOCATION, permisionName)
                || TextUtils.equals(Manifest.permission.ACCESS_FINE_LOCATION, permisionName)
        ) {
            return "定位";
        } else if (TextUtils.equals(Manifest.permission.READ_PHONE_STATE, permisionName)) {
            return "获取设备信息";
        }
        return "";
    }

    public interface PermistionResult {
        void onResult(boolean isSuccess);
    }
}
