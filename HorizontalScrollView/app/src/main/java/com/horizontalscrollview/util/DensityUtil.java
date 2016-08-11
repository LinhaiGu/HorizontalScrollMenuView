package com.horizontalscrollview.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

/**
 * Created by glh on 2016-08-11.
 */
public class DensityUtil {

    private DensityUtil(){}

    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * dip转化像素
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
