package com.cclong.umshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cclong.sharelibrary.UMShareManager;
import com.cclong.sharelibrary.UMTools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void popShare(View view) {
        UMShareManager umShareManager = new UMShareManager.Builder(this, "乐桔分享")
                .setResId(R.mipmap.ic_launcher)
                .setLink("http://www.baidu.com")
                .setMessage("一切尽在不言中")
                .create();
        umShareManager.showSharePlatForm();
    }
}
