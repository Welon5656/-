package com.example.wangyi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummyFragment extends Fragment 
{
	public static final String Title = "title";
	// 该方法的返回值就是该Fragment显示的View组件
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment, container, false);
		TextView textView = (TextView) fragmentView.findViewById(R.id.textView);
		//获取Activity传递过来的参数
		Bundle mBundle = getArguments();
		String title = mBundle.getString(Title);
		textView.setText(title);
		//返回View
		return fragmentView;
	}
}