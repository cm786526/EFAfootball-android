package com.example.cnm.efafootball;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import contants.contants;
import fragments.Fragment_home;
import fragments.Fragment_match;
import fragments.Fragment_mine;
import fragments.Fragment_team;

/*
 * &#x4e3b;Activity
 *
 * @author wwj_748
 *
 */
public class HomeMainActivity extends Activity implements OnClickListener {

    // 四个tab布局
    private RelativeLayout homeLayout,matchLayout,teamLayout,mineLayout;

    // 底部标签切换的Fragment
    private Fragment homeFragment, matchFragment, teamFragment,mineFragment,
            currentFragment;
    // 底部标签图片
    private ImageView homeImg, matchImg, teamImg,mineImg;
    // 底部标签的文本
    private TextView homeTv, matchTv, teamTv,mineTv;
    //中间的四个webview控件
    private WebView homeWeb,matchWeb,teamWeb,mineWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        //注册EventBus  
        EventBus.getDefault().register(HomeMainActivity.this);
        initUI();
        initTab();

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        homeFragment = new Fragment_home();
        teamFragment = new Fragment_team();
        mineFragment = new Fragment_mine();
        matchFragment = new Fragment_match();
        // 添加显示第一个fragment
        getFragmentManager().beginTransaction().add(R.id.fragement_content, homeFragment)
                .add(R.id.fragement_content, matchFragment,"matchTag")
                .add(R.id.fragement_content,teamFragment,"teamTag")
                .add(R.id.fragement_content,mineFragment,"mineTag")
                .hide(matchFragment).hide(teamFragment).hide(mineFragment)
                .show(homeFragment)
                .commit();
        homeLayout = (RelativeLayout) findViewById(R.id.home_layout);
        matchLayout = (RelativeLayout) findViewById(R.id.match_layout);
        teamLayout = (RelativeLayout) findViewById(R.id.team_layout);
        mineLayout=(RelativeLayout)findViewById(R.id.mine_layout);
        homeLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        teamLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);

        homeImg = (ImageView) findViewById(R.id.home_img);
        matchImg = (ImageView) findViewById(R.id.match_img);
        teamImg = (ImageView) findViewById(R.id.team_img);
        mineImg=(ImageView)findViewById(R.id.mine_img);

        homeTv = (TextView) findViewById(R.id.home_txt);
        matchTv = (TextView) findViewById(R.id.match_txt);
        teamTv = (TextView) findViewById(R.id.team_txt);
        mineTv =(TextView)findViewById(R.id.mine_txt);
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
            // 记录当前Fragment
            currentFragment = homeFragment;
            // 设置图片文本的变化
            homeImg.setImageResource(R.drawable.home_checked);
            homeTv.setTextColor(getResources()
                    .getColor(R.color.bottomtab_normal));
            matchImg.setImageResource(R.drawable.match_gray);
            matchTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
            teamImg.setImageResource(R.drawable.team_gray);
            teamTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
            mineImg.setImageResource(R.drawable.mine_gray);
            mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_layout: // 首页
                clickTab1Layout();
                break;
            case R.id.match_layout: // 赛事
                clickTab2Layout();
                break;
            case R.id.team_layout: // 球队
                clickTab3Layout();
                break;
            case R.id.mine_layout:  //个人
                clickTab4Layout();
            default:
                break;
        }
    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        if (homeFragment == null) {
            homeFragment = new Fragment_home();
        }
        addOrShowFragment(getFragmentManager().beginTransaction(), homeFragment);

        // 设置底部tab变化
        homeImg.setImageResource(R.drawable.home_checked);
        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        matchImg.setImageResource(R.drawable.match_gray);
        matchTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        teamImg.setImageResource(R.drawable.team_gray);
        teamTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.mine_gray);
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (matchFragment == null) {
            matchFragment = new Fragment_match();
        }
        addOrShowFragment(getFragmentManager().beginTransaction(), matchFragment);

        homeImg.setImageResource(R.drawable.home_gray);
        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        matchImg.setImageResource(R.drawable.match_checked);
        matchTv.setTextColor(getResources().getColor(
                R.color.bottomtab_press));
        teamImg.setImageResource(R.drawable.team_gray);
        teamTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.mine_gray);
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }
    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (teamFragment == null) {
            teamFragment = new Fragment_team();
        }
        addOrShowFragment(getFragmentManager().beginTransaction(), teamFragment);

        homeImg.setImageResource(R.drawable.home_gray);
        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        matchImg.setImageResource(R.drawable.match_gray);
        matchTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        teamImg.setImageResource(R.drawable.team_checked);
        teamTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        mineImg.setImageResource(R.drawable.mine_gray);
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }
    /**
     * 点击第四个tab
     */
    private void clickTab4Layout() {
        if (mineFragment == null) {
            mineFragment = new Fragment_mine();
        }
        addOrShowFragment(getFragmentManager().beginTransaction(), mineFragment);

        homeImg.setImageResource(R.drawable.home_gray);
        homeTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        matchImg.setImageResource(R.drawable.match_gray);
        matchTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        teamImg.setImageResource(R.drawable.team_gray);
        teamTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        mineImg.setImageResource(R.drawable.mine_checked);
        mineTv.setTextColor(getResources().getColor(R.color.bottomtab_press));

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fragement_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MyEventBus event) {

        int msg=event.getMsg();
        switch (msg){
            case contants.JOIN_TEAM:
                clickTab3Layout();
                break;
            case contants.MATCH_SIGN_UP:
                clickTab2Layout();
                break;
            default:
                break;
        }
    }

    @Override
     protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus  
     }
}