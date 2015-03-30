package com.example.wangyi;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Description: <br/>
 * website: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2014, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class MainActivity extends FragmentActivity
	implements ActionBar.TabListener {
	
	private String [] TabTitles;
	private ViewPager viewPager;
	private ActionBar actionBar;
	private FragmentPagerAdapter pagerAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// TabTitles存储每个项的标题
		TabTitles = getResources().getStringArray(R.array.tab_title);
		// 获取ActionBar对象
		actionBar = getActionBar();
		// 获取ViewPager
		viewPager = (ViewPager) findViewById(R.id.pager);
		// 创建一个FragmentPagerAdapter对象，该对象负责为ViewPager提供多个Fragment
		pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			// 获取第position位置的Fragment
			@Override
			public Fragment getItem(int position) {
				Fragment fragment = new DummyFragment();
				Bundle args = new Bundle();
				args.putString(DummyFragment.Title, TabTitles[position]);
				fragment.setArguments(args);
				return fragment;
			}
			// 该方法的返回值i表明该Adapter总共包括多少个Fragment
			@Override
			public int getCount() {
				return TabTitles.length;
			}
		};
		// 设置ActionBar使用Tab导航方式
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//为ActionBar添加Tab并设置TabListener
		for(int i=0; i<TabTitles.length; i++) {
			ActionBar.Tab tab = actionBar.newTab();
			tab.setText(TabTitles[i]);
			tab.setTabListener(this);
			actionBar.addTab(tab, i);
		}
		// 为ViewPager组件设置FragmentPagerAdapter
		viewPager.setAdapter(pagerAdapter);  //①
		// 为ViewPager组件绑定事件监听器
		viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					// 当ViewPager显示的Fragment发生改变时激发该方法
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}	
				});
		}
	
	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	}

	// 当指定Tab被选中时激发该方法
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	}
}
