package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class HeartView extends View {

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private int defaultWidth;
	
	/**
	 * View缺省高度
	 */
	private int defaultHeight;
	
	/**
	 * 是否需要重新计算各个点、矩形等的坐标
	 */
	private boolean isPointsDirty = false;
	
	private int mContentWidth = 0;
	
	private Path mHeartPath = new Path();
	
	private float rate = 10; // 半径变化率
	
	public HeartView(Context context) {
		super(context);
	}

	public HeartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		//只要是不是精确指定的，就设置为默认宽高。
		if (widthMode != MeasureSpec.EXACTLY) {
			width = defaultWidth;
		}
		if (heightMode != MeasureSpec.EXACTLY) {
			height = defaultHeight;
		}
		mContentWidth = Math.min(width, height);
		setMeasuredDimension(mContentWidth + getPaddingLeft() + getPaddingRight()
				, mContentWidth + getPaddingTop() + getPaddingBottom());
		isPointsDirty = true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isPointsDirty) {
			initData();
			isPointsDirty = false;
		}
		canvas.clipPath(mHeartPath);
		canvas.drawColor(Color.RED);
	}

	/*private void initData() {
		// 得到屏幕的长宽的一半  
        int px = getMeasuredWidth() / 2;  
        int py = getMeasuredHeight() / 2;  
        // 路径的起始点  
        mHeartPath.moveTo(px, py - 5 * rate);  
        // 根据心形函数画图  
        for (double i = 0; i <= 2 * Math.PI; i += 0.001) {  
            float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));  
            float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));  
            x *= rate;  
            y *= rate;  
            x = px - x;  
            y = py - y;  
            mHeartPath.lineTo(x, y);  
        }  
        
	}*/
	
	private void initData() {
		mHeartPath.reset();
		
		mHeartPath.moveTo(297.29747f,550.86823f);
		mHeartPath.cubicTo(283.52243f,535.43191f,249.1268f,505.33855f, 220.86277f,483.99412f);
		mHeartPath.cubicTo(137.11867f,420.75228f,125.72108f,411.5999f,91.719238f,380.29088f);
		mHeartPath.cubicTo(29.03471f,322.57071f,2.413622f,264.58086f,2.5048478f,185.95124f);
		mHeartPath.cubicTo(2.5493594f,147.56739f,5.1656152f,132.77929f,15.914734f,110.15398f);
		mHeartPath.cubicTo(34.151433f,71.768267f,61.014996f,43.244667f,95.360052f,25.799457f);
		mHeartPath.cubicTo(119.68545f,13.443675f,131.6827f,7.9542046f,172.30448f,7.7296236f);
		mHeartPath.cubicTo(214.79777f,7.4947896f,223.74311f,12.449347f,248.73919f,26.181459f);
		mHeartPath.cubicTo(279.1637f,42.895777f,310.47909f,78.617167f,316.95242f,103.99205f);
		
		mHeartPath.lineTo(320.95052f,119.66445f);
		mHeartPath.lineTo(330.81015f,98.079942f);
		
		mHeartPath.cubicTo(386.52632f,-23.892986f,564.40851f,-22.06811f,626.31244f,101.11153f);
		mHeartPath.cubicTo(645.95011f,140.18758f,648.10608f,223.6247f,630.69256f,270.6244f);
		mHeartPath.cubicTo(607.97729f,331.93377f,565.31255f,378.67493f,466.68622f,450.30098f);
		mHeartPath.cubicTo(402.0054f,497.27462f,328.80148f,568.34684f,323.70555f,578.32901f);
		mHeartPath.cubicTo(317.79007f,589.91654f,323.42339f,580.14491f,297.29747f,550.86823f);
		
		mHeartPath.close();
		
	}
}
