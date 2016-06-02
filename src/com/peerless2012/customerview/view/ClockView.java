package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;

public class ClockView extends View{

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
	
	private int centerX;
	
	private int centerY;
	
	private int radius;
	
	private int clockEdgeWidth = 2;
	
	public ClockView(Context context) {
		this(context,null);
	}

	public ClockView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
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
		
		centerX = mContentWidth / 2 + getPaddingLeft();
		centerY = mContentWidth / 2 + getPaddingTop();
		radius = mContentWidth / 2 -clockEdgeWidth;
	}

	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawClockBg(canvas);
		
		drawHand(canvas);
	}

	/**
	 * 绘制时针、分针、秒针
	 * @param canvas
	 */
	private void drawHand(Canvas canvas) {
		canvas.save();
		canvas.translate(centerX, centerY);
		
		//时针
		canvas.save();
		mPaint.setStrokeWidth(4);
		canvas.rotate(30);
		canvas.drawLine(0, -10, 0, radius * 0.4f, mPaint);
		canvas.restore();
		//分针
		canvas.save();
		mPaint.setStrokeWidth(2);
		canvas.rotate(90);
		canvas.drawLine(0, -10, 0, radius * 0.6f, mPaint);
		canvas.restore();
		//秒针
		canvas.save();
		mPaint.setStrokeWidth(1);
		canvas.rotate(60);
		canvas.drawLine(0, -10, 0, radius * 0.8f, mPaint);
		canvas.restore();
		//红圈
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Style.STROKE);
		canvas.drawCircle(0, 0, 3, mPaint);
		canvas.restore();
	}

	/**
	 * 绘制表盘背景和时间
	 * @param canvas
	 */
	private void drawClockBg(Canvas canvas) {
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.GRAY);
		mPaint.setStyle(Style.STROKE);
		canvas.drawCircle(centerX, centerY, radius, mPaint);
		
		//绘制表盘刻度
		mPaint.setStyle(Style.FILL);
		canvas.save();
		canvas.translate(centerX, centerY);
		for (int i = 0; i < 36; i++) {
			canvas.drawLine(0, radius, 0, radius - (i % 3 == 0 ? 10 : 5), mPaint);
			canvas.rotate(10);
		}
		canvas.restore();
		
//		canvas.save();
//		canvas.translate(centerX, centerY);
//		int x = 0;
//		int y = 0;
//		for (int i = 0; i < 12; i++) {
//			x = (int) (Math.cos(i * 10) * radius);
//			y = (int) (Math.sin(i * 10) * radius);
//			canvas.drawText("" + i, x, y, mPaint);
//		}
//		canvas.restore();
	}
}
