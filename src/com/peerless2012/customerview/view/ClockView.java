package com.peerless2012.customerview.view;

import java.util.Calendar;
import java.util.Locale;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月3日 上午12:30:54
* @Version V1.0
* @Description: 模仿钟表的View
*/
public class ClockView extends View{

	public final static int TEXT_MODE_FIXED = 0;
	public final static int TEXT_MODE_AROUND = 1;
	
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
	
	private int[] handDegrees = new int[3];
	
	private int mTextMode = TEXT_MODE_FIXED;
	
	public ClockView(Context context) {
		this(context,null);
	}

	public ClockView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mTime = Calendar.getInstance(Locale.CHINESE);
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
		canvas.translate(centerX, centerY);
		
		drawClockBg(canvas);
		
		drawHand(canvas);
	}

	/**
	 * 绘制时针、分针、秒针
	 * @param canvas
	 */
	private void drawHand(Canvas canvas) {
		//时针
		canvas.save();
		mPaint.setStrokeWidth(4);
		canvas.rotate(handDegrees[0]);
		canvas.drawLine(0, 10, 0, - radius * 0.4f, mPaint);
		canvas.restore();
		//分针
		canvas.save();
		mPaint.setStrokeWidth(2);
		canvas.rotate(handDegrees[1]);
		canvas.drawLine(0, 10, 0, - radius * 0.6f, mPaint);
		canvas.restore();
		//秒针
		canvas.save();
		mPaint.setStrokeWidth(1);
		canvas.rotate(handDegrees[2]);
		canvas.drawLine(0, 10, 0, - radius * 0.8f, mPaint);
		canvas.restore();
		//红圈
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Style.STROKE);
		canvas.drawCircle(0, 0, 3, mPaint);
	}

	/**
	 * 绘制表盘背景和时间
	 * @param canvas
	 */
	private void drawClockBg(Canvas canvas) {
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.GRAY);
		mPaint.setStyle(Style.STROKE);
		canvas.drawCircle(0, 0, radius, mPaint);
		
		//绘制表盘刻度
		mPaint.setStyle(Style.FILL);
		canvas.save();
		for (int i = 0; i < 60; i++) {
			canvas.drawLine(0, radius, 0, radius - (i % 5 == 0 ? 10 : 5), mPaint);
			canvas.rotate(6);
		}
		canvas.restore();
		
		canvas.save();
		int x = 0;
		int y = 0;
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		float offset = (fontMetrics.descent - fontMetrics.ascent)/2;
		mPaint.setTextAlign(Align.CENTER);
		for (int i = 0; i < 12; i++) {
			y = radius - 30;
			canvas.translate(x, -y);
			if (mTextMode == TEXT_MODE_FIXED) {
				canvas.rotate( -30 * i);
			}
			canvas.drawText("" + i, 0, offset, mPaint);
			if (mTextMode == TEXT_MODE_FIXED) {
				canvas.rotate( 30 * i);
			}
			canvas.translate(x, y);
			canvas.rotate(30);
		}
		canvas.restore();
	}
	
	private Calendar mTime;
	
	private boolean mAttached;
	
	private final Runnable mTicker = new Runnable() {
        public void run() {
        	onTimeChanged();
        	mTime.setTimeInMillis(System.currentTimeMillis());
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            getHandler().postAtTime(mTicker, next);
        }
    };
    
    @Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!mAttached) {
            mAttached = true;
            mTicker.run();
        }
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
            getHandler().removeCallbacks(mTicker);
            mAttached = false;
        }
	}
	
	private void onTimeChanged() {
        mTime.setTimeInMillis(System.currentTimeMillis());
        handDegrees[0] = (mTime.get(Calendar.HOUR) % 12) * 30;
        handDegrees[1] = mTime.get(Calendar.MINUTE) * 6;
        handDegrees[2] = mTime.get(Calendar.SECOND) * 6;
//        Log.i("ClockView", handDegrees[0] + " " + handDegrees[1] + " " + handDegrees[2]);
        invalidate();
    }
}
