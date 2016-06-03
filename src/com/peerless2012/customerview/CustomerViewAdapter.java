package com.peerless2012.customerview;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月3日 上午11:10:27
* @Version V1.0
* @Description: 自定义View展示的适配器
*/
public class CustomerViewAdapter extends PagerAdapter{

	private int[] mLayoutRes = null;
	
	public CustomerViewAdapter(int[] mLayoutRes) {
		super();
		this.mLayoutRes = mLayoutRes;
	}

	@Override
	public int getCount() {
		return mLayoutRes == null ? 0 : mLayoutRes.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View pager = LayoutInflater.from(container.getContext()).inflate(mLayoutRes[position], container, false);
		container.addView(pager);
		return pager;
	}
}
