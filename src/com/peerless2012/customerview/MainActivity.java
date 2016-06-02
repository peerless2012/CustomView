package com.peerless2012.customerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月3日 上午11:30:37
* @Version V1.0
* @Description: 展示
*/
public class MainActivity extends Activity {

	private int [] mLayoutRes = new int[]{
			R.layout.view_clock,R.layout.view_record,R.layout.view_wifi
			,R.layout.view_qq_health
	};
	
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mViewPager.setLayoutParams(params);
		setContentView(mViewPager);
		mViewPager.setAdapter(new CustomerViewAdapter(mLayoutRes));
	}
	
}
