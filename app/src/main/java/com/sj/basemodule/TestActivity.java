package com.sj.basemodule;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        View view = getWindow().getDecorView();
        ViewGroup contentView = view.findViewById(android.R.id.content);
        View childView = contentView.getChildAt(0);
    }
}
