package com.peerless2012.customerview.view;

import com.peerless2012.customerview.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View{

	private Drawable mDrawable;
	
	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//3fb9ff
		mDrawable = getResources().getDrawable(R.drawable.pic_planet01);
//		mDrawable.setColorFilter(new LightingColorFilter(0x01111111, 0x00000000));
		mDrawable.setAlpha(40);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDrawable.setBounds(0, 0, w, h);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		mDrawable.draw(canvas);
	}
	
}
