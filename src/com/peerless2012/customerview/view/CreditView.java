package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.SweepGradient;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016年7月25日 下午3:12:36
* @Version V1.0
* @Description: 支付宝信用
*/
public class CreditView extends View {
	
	public CreditView(Context context) {
		this(context,null);
	}

	public CreditView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public CreditView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setWillNotDraw(false);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
