package com.lvda.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    SmartRefreshLayout scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView=findViewById(R.id.scrollView);
        scrollView.autoRefresh();
    }
}
