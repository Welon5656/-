package com.example.wangyi;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity
	implements ActionBar.TabListener {
	
	private String [] TabTitles;
	private ViewPager viewPager;
	private ActionBar actionBar;
	private FragmentPagerAdapter pagerAdapter;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout leftView,rightView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getOverflowMenu();
		init();
	}
	
	private void init() {
		/* TabTitles存储每个项的标题 */
		TabTitles = getResources().getStringArray(R.array.tab_title);
		actionBar = getActionBar();
		viewPager = (ViewPager) findViewById(R.id.pager);
		/* 创建一个FragmentPagerAdapter对象，该对象负责为ViewPager提供多个Fragment */
		pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			/* 获取第position位置的Fragment */
			@Override
			public Fragment getItem(int position) {
				Fragment fragment = new DummyFragment();
				Bundle args = new Bundle();
				args.putString(DummyFragment.Title, TabTitles[position]);
				fragment.setArguments(args);
				return fragment;
			}
			/* 该方法的返回值表明该Adapter总共包括多少个Fragment */
			@Override
			public int getCount() {
				return TabTitles.length;
			}
		};
		/* 设置ActionBar使用Tab导航方式 */
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		/* 为ActionBar添加Tab并设置TabListener */
		for(int i=0; i<TabTitles.length; i++) {
			ActionBar.Tab tab = actionBar.newTab();
			tab.setText(TabTitles[i]);
			tab.setTabListener(this);
			actionBar.addTab(tab, i);
		}
		actionBar.setHomeButtonEnabled(true);      // 
		actionBar.setDisplayHomeAsUpEnabled(true); //
		actionBar.setDisplayShowHomeEnabled(true); //
		// 为ViewPager组件设置FragmentPagerAdapter
		viewPager.setAdapter(pagerAdapter);
		// 为ViewPager组件绑定事件监听器
		viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					// 当ViewPager显示的Fragment发生改变时激发该方法
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                   /* host Activity */
                mDrawerLayout,          /* DrawerLayout object */
                R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,   /* "open drawer" description for accessibility */
                R.string.drawer_close   /* "close drawer" description for accessibility */
                ) {
			/* openDrawer、closeDrawer等方法可以在此重写 */
        };

        // 为ActionBarDrawerToggle对象设置监听，还添加了相关方法如openDrawer、closeDrawer等
        mDrawerLayout.setDrawerListener(new MyDrawerListener());
        leftView = (LinearLayout) findViewById(R.id.left_drawer);
        rightView = (LinearLayout) findViewById(R.id.right_drawer);
        MenuLandscapeLinearLayout LeftLinearLayout = new MenuLandscapeLinearLayout(this, R.layout.leftlinear);  
        MenuLandscapeLinearLayout RightLinearLayout = new MenuLandscapeLinearLayout(this, R.layout.rightlinear);
        leftView.addView(LeftLinearLayout);
        rightView.addView(RightLinearLayout);
	}

	/* ActionBar不够空间显示和不显示的item将会放置在OverflowMenu中 */
	private void getOverflowMenu() {
		ViewConfiguration viewConfig = ViewConfiguration.get(this);
		
		try {
			Field overflowMenuField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(null != overflowMenuField) {
				overflowMenuField.setAccessible(true);
				overflowMenuField.setBoolean(viewConfig, false);
			}
		}
		catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* 监听DrawerLayout的各个状态 */
	private class MyDrawerListener implements DrawerLayout.DrawerListener {

		@Override
		public void onDrawerClosed(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/* 右边栏的相关方法 */
	public void openRightLayout() {
		if (mDrawerLayout.isDrawerOpen(leftView))
			mDrawerLayout.closeDrawer(leftView);
		else if (mDrawerLayout.isDrawerOpen(rightView))
			mDrawerLayout.closeDrawer(rightView);
		else
			mDrawerLayout.openDrawer(rightView);
	}
	
	public class MenuLandscapeLinearLayout extends LinearLayout {
		/* 构造函数 */
	    public MenuLandscapeLinearLayout(Context context, int res) {
	    	super(context);
	    	// TODO Auto-generated constructor stub
	    	//加载需要的属性，加载子Layout
	    	((Activity) getContext()).getLayoutInflater().inflate(res, this); 
	    }	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerLayout.isDrawerOpen(rightView)) {
			mDrawerLayout.closeDrawer(rightView);
		}
		else {
			mDrawerToggle.onOptionsItemSelected(item);
		}
        switch(item.getItemId()) {
		case R.id.menu_log_in:
			System.out.print("ok");
			openRightLayout();
			break;
		case R.id.action_settings:
			System.out.print("ok");
			//finish();
			break;
		}
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	}

	/* 当指定Tab被选中时激发该方法 */
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /* syncState()会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标 */
        mDrawerToggle.syncState();
    }
	
	/* 设备配置改变时 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
		
}
