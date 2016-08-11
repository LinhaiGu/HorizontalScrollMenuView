# HorizontalScrollMenuView
Horizontal scrolling menu


layout:


<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.horizontalscrollview.view.HorizontalScrollMenuView
        android:id="@+id/hs_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/white"
        android:layout_centerVertical="true"/>


</RelativeLayout>



Activity:

package com.horizontalscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.horizontalscrollview.view.HorizontalScrollMenuView;
import com.horizontalscrollview.view.adapter.BaseMenuAdapter;

public class MainActivity extends AppCompatActivity {

    private int[] mDrawableId={R.drawable.image1,R.drawable.image3,R.drawable.image3};
    private String[] mTitle={"菜单一","菜单二","菜单三"};

    private HorizontalScrollMenuView mHorizontalScrollMenuView;
    private MyMenuAdapter mMyMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        mHorizontalScrollMenuView= (HorizontalScrollMenuView) findViewById(R.id.hs_menu);
        mMyMenuAdapter=new MyMenuAdapter();
        mHorizontalScrollMenuView.setAdapter(mMyMenuAdapter);
        mHorizontalScrollMenuView.notifyDataSetChanged();
    }


    class MyMenuAdapter extends BaseMenuAdapter{
        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public View getView(final int position) {
            View root= LayoutInflater.from(MainActivity.this).inflate(R.layout.menu_layout,null);
            ImageView imageView= (ImageView) root.findViewById(R.id.iv_icon);
            TextView tv_title= (TextView) root.findViewById(R.id.tv_title);
            tv_title.setText(mTitle[position]);
            imageView.setImageResource(mDrawableId[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"点击了"+mTitle[position],Toast.LENGTH_SHORT).show();
                }
            });
            return root;
        }

        @Override
        public int getViewWidth() {
            return 250;
        }
    }


}
