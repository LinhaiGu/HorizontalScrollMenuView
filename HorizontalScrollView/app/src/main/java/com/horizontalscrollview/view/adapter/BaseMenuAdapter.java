package com.horizontalscrollview.view.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 菜单的抽象类，用户子菜单View的保存
 * Created by glh on 2016-08-11.
 */
public abstract class BaseMenuAdapter implements MenuAdapter{

    private SparseArray<View> mMenuArray=new SparseArray<>();

    /**
     * 获取子View的宽度，必传
     * @return
     */
    public abstract int getViewWidth();

    public SparseArray<View> getView(){
        mMenuArray.clear();
        for(int index=0,length=getCount();index<length;index++){
            mMenuArray.append(index,getView(index));
        }
        return mMenuArray;
    }

}
