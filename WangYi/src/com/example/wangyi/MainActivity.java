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
		/* TabTitles�洢ÿ����ı��� */
		TabTitles = getResources().getStringArray(R.array.tab_title);
		actionBar = getActionBar();
		viewPager = (ViewPager) findViewById(R.id.pager);
		/* ����һ��FragmentPagerAdapter���󣬸ö�����ΪViewPager�ṩ���Fragment */
		pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			/* ��ȡ��positionλ�õ�Fragment */
			@Override
			public Fragment getItem(int position) {
				Fragment fragment = new DummyFragment();
				Bundle args = new Bundle();
				args.putString(DummyFragment.Title, TabTitles[position]);
				fragment.setArguments(args);
				return fragment;
			}
			/* �÷����ķ���ֵ������Adapter�ܹ��������ٸ�Fragment */
			@Override
			public int getCount() {
				return TabTitles.length;
			}
		};
		/* ����ActionBarʹ��Tab������ʽ */
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		/* ΪActionBar���Tab������TabListener */
		for(int i=0; i<TabTitles.length; i++) {
			ActionBar.Tab tab = actionBar.newTab();
			tab.setText(TabTitles[i]);
			tab.setTabListener(this);
			actionBar.addTab(tab, i);
		}
		actionBar.setHomeButtonEnabled(true);      // 
		actionBar.setDisplayHomeAsUpEnabled(true); //
		actionBar.setDisplayShowHomeEnabled(true); //
		// ΪViewPager�������FragmentPagerAdapter
		viewPager.setAdapter(pagerAdapter);
		// ΪViewPager������¼�������
		viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					// ��ViewPager��ʾ��Fragment�����ı�ʱ�����÷���
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
			/* openDrawer��closeDrawer�ȷ��������ڴ���д */
        };

        // ΪActionBarDrawerToggle�������ü��������������ط�����openDrawer��closeDrawer��
        mDrawerLayout.setDrawerListener(new MyDrawerListener());
        leftView = (LinearLayout) findViewById(R.id.left_drawer);
        rightView = (LinearLayout) findViewById(R.id.right_drawer);
        MenuLandscapeLinearLayout LeftLinearLayout = new MenuLandscapeLinearLayout(this, R.layout.leftlinear);  
        MenuLandscapeLinearLayout RightLinearLayout = new MenuLandscapeLinearLayout(this, R.layout.rightlinear);
        leftView.addView(LeftLinearLayout);
        rightView.addView(RightLinearLayout);
	}

	/* ActionBar�����ռ���ʾ�Ͳ���ʾ��item���������OverflowMenu�� */
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
	
	/* ����DrawerLayout�ĸ���״̬ */
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
	
	/* �ұ�������ط��� */
	public void openRightLayout() {
		if (mDrawerLayout.isDrawerOpen(leftView))
			mDrawerLayout.closeDrawer(leftView);
		else if (mDrawerLayout.isDrawerOpen(rightView))
			mDrawerLayout.closeDrawer(rightView);
		else
			mDrawerLayout.openDrawer(rightView);
	}
	
	public class MenuLandscapeLinearLayout extends LinearLayout {
		/* ���캯�� */
	    public MenuLandscapeLinearLayout(Context context, int res) {
	    	super(context);
	    	// TODO Auto-generated constructor stub
	    	//������Ҫ�����ԣ�������Layout
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

	/* ��ָ��Tab��ѡ��ʱ�����÷��� */
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
        /* syncState()���Զ���actionBar����, �����ص�ͼƬ��ʾ����action�ϣ���������ã�Ҳ�����г����Ч����������Ĭ�ϵ�ͼ�� */
        mDrawerToggle.syncState();
    }
	
	/* �豸���øı�ʱ */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
		
}
