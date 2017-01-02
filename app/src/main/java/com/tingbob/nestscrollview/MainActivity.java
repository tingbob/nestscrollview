package com.tingbob.nestscrollview;

import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TabLayout tabLayout;

    private ViewPager mViewPager;

    private View bottomView;
    private View mPopupParentView = null; // 选择区域的view
    private PopupWindow mPopupWindow = null;
    private int screen_width = 0;
    private int screen_height = 0;
    private View mAnchorView;
    private  int[] location = new int[2];
    TranslateAnimation animation;// 出现的动画效果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screen_width = metric.widthPixels; // 屏幕宽度（像素）
        screen_height = metric.heightPixels; // 屏幕高度（像素）

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //使用系统navi back
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        ivBack = (ImageView) toolbar.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //张开
                    tvTitle.setAlpha(0);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //收缩
                    tvTitle.setAlpha(1);
                } else {
                    float alpha = (Math.abs((float) verticalOffset))/(float)appBarLayout.getTotalScrollRange();
//                    Log.d("nestscrollview", "verticalOffset = " + verticalOffset);
//                    Log.d("nestscrollview", "getTotalScrollRange = " + appBarLayout.getTotalScrollRange());
//                    Log.d("nestscrollview", "alpha = " + alpha);

                    tvTitle.setAlpha(alpha);
                }
            }
        });

        ImageView ivImage = (ImageView)findViewById(R.id.ivImage);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("DRecycleView"));
        tabLayout.addTab(tabLayout.newTab().setText("WebView"));
        tabLayout.addTab(tabLayout.newTab().setText("Other"));
        tabLayout.setupWithViewPager(mViewPager);

        bottomView = findViewById(R.id.footer);
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPupupWindow();
            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DRecyclerViewFragment.newInstance(), "DRecycleView");
        adapter.addFragment(WebViewFragment.newInstance(), "WebView");
        adapter.addFragment(OtherFragment.newInstance(), "Other");
        mViewPager.setAdapter(adapter);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private void showPupupWindow() {
        mPopupParentView = LayoutInflater.from(this).inflate(R.layout.bottom_pop, null);
        initPopupWindow(mPopupParentView);

        View blankView = mPopupParentView.findViewById(R.id.tv_bottom_pop);
        blankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
        mPopupParentView.setAnimation(animation);
        mPopupParentView.startAnimation(animation);
        mPopupWindow.showAtLocation(mAnchorView, Gravity.NO_GRAVITY, location[0], location[1] - mPopupWindow.getHeight());
    }

    public void initPopupWindow(View view) {
        /* 第一个参数弹出显示view 后两个是窗口大小 */
        if (mPopupWindow == null) {
            if (mAnchorView == null) {
                mAnchorView = bottomView;
            }
            mAnchorView.getLocationOnScreen(location);// 获取控件在屏幕中的位置,方便展示Popupwindow
            screen_height = location[1];
            animation = new TranslateAnimation(0, 0, screen_height, 0);
            animation.setDuration(500);
            mPopupWindow = new PopupWindow(screen_width, screen_height);
        }
        mPopupWindow.setContentView(view);
        /* 设置背景显示 */
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		/* 设置触摸外面时消失 */
        // mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
		/* 设置点击menu以外其他地方以及返回键退出 */
        mPopupWindow.setFocusable(true);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        /**
         * 1.解决再次点击MENU键无反应问题 2.sub_view是PopupWindow的子View
         */
        view.setFocusableInTouchMode(true);
    }
}
