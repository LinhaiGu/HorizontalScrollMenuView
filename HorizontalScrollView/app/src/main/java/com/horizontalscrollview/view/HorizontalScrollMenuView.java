package com.horizontalscrollview.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.horizontalscrollview.R;
import com.horizontalscrollview.util.DensityUtil;
import com.horizontalscrollview.view.adapter.BaseMenuAdapter;

/**
 * 水平滚动的菜单
 * Created by glh on 2016-08-11.
 */
public class HorizontalScrollMenuView extends RelativeLayout{

    private Context mContext;

    private View mRootView;
    private LinearLayout ll_menu;//菜单容器
    private ObservableHorizontalScrollView mObservableHorizontalScrollView;//滚动容器
    private int mMenuIndex;
    private int mScroll;
    private int interval;//菜单最左边和最右边的距离
    private int mMiddle;//菜单间隔距离
    private int mViewWidth;//菜单宽度
    private MyHandler mMyHandler;
    private BaseMenuAdapter mBaseMenuAdapter;

    public HorizontalScrollMenuView(Context context) {
        this(context, null);

    }

    public HorizontalScrollMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        mMyHandler=new MyHandler();
        interval=(DensityUtil.getWindowWidth(mContext) - DensityUtil.dip2px(250)) / 2;
        mMiddle=DensityUtil.dip2px(25);
        mViewWidth=DensityUtil.dip2px(250);
        initView();
        initEvent();
    }

    private void initView(){
        mRootView= LayoutInflater.from(mContext).inflate(R.layout.horizontal_scroll_layout,this,true);
        ll_menu= (LinearLayout) mRootView.findViewById(R.id.ll_menu);
        mObservableHorizontalScrollView= (ObservableHorizontalScrollView) mRootView.findViewById(R.id.obh_view);
    }

    /**
     * 添加菜单
     */
    private void addMenu(){
        ll_menu.removeAllViews();
        SparseArray<View> viewSparseArray=mBaseMenuAdapter.getView();
        View intervalView;
        for(int index=0,length=viewSparseArray.size();index<length;index++){
            if (index == 0) {
                /**
                 * <p>
                 *     在最左边增加一个（屏宽-菜单宽）/2的宽度的View,
                 *     目的在于使第一个菜单居中显示。
                 * </>
                 */
                intervalView = new View(mContext);
                intervalView.setLayoutParams(new LayoutParams(interval, 1));
                ll_menu.addView(intervalView);
            }
            ll_menu.addView(viewSparseArray.get(index));
            if (index == length-1) {
                /**
                 * <p>
                 *     在最右边增加一个（屏宽-菜单宽）/2的宽度的View,
                 *     目的在于使最后一个菜单居中显示。
                 * </>
                 */
                intervalView = new View(mContext);
                intervalView.setLayoutParams(new LayoutParams(interval, 1));
                ll_menu.addView(intervalView);
            }else{
                /**
                 * 两个菜单之间留一点空隙
                 */
                intervalView = new View(mContext);
                intervalView.setLayoutParams(new LayoutParams(mMiddle, 1));
                ll_menu.addView(intervalView);
            }
        }
    }

    private void initEvent(){
        mObservableHorizontalScrollView.setHorizontalScrollViewListener(new ObservableHorizontalScrollView.HorizontalScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView scrollView,
                                        int x, int y, int oldx, int oldy) {
                mMenuIndex = x / mViewWidth;//计算当前位置
                mScroll = x % mViewWidth;
                if (mScroll >= 0.5) {
                    mMenuIndex++;
                }
                if (oldx - x > 0) {
                    /**
                     * 菜单往右滑动
                     */
                    mMenuIndex--;
                }

            }
        });

        mObservableHorizontalScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mMyHandler.sendEmptyMessage(0);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 设置View
     * @param adapter
     */
    public void setAdapter(BaseMenuAdapter adapter){
        this.mBaseMenuAdapter=adapter;
        this.interval =(DensityUtil.getWindowWidth(mContext) - DensityUtil.dip2px(mBaseMenuAdapter.getViewWidth())) / 2;
        this.mViewWidth=DensityUtil.dip2px(mBaseMenuAdapter.getViewWidth());
    }

    /**
     * 刷新
     */
    public void notifyDataSetChanged(){
        addMenu();
    }

    /**
     * 设置菜单间隔
     * @param middle
     */
    public void setMiddle(int middle){
        mMiddle=DensityUtil.dip2px(middle);
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mObservableHorizontalScrollView.smoothScrollTo(mMenuIndex * (mViewWidth + mMiddle), 0);
                    break;
            }
        }
    }

}
