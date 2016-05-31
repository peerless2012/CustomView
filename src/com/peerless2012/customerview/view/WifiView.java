package com.peerless2012.customerview.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年5月30日 下午10:59:13
* @Version V1.0
* @Description: 自定义话筒录音的View （drawable下wifi）
*/
public class WifiView extends View {
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private int defaultWidth;
	
	private int defaultHeight;
	
	private float mCircleWidth;
	
	private float mWifiCircleWidth;
	
	private float mWifiCircleGapWidth;
	
	private boolean isPointsDirty = false;
	
	private PointF mCenterPointF;
	
	private PointF mWifiPointF;
	
	private RectF mWifiRectF;
	
	private float mProgress = 0.7f;
	
	public WifiView(Context context) {
		this(context, null);
	}

	public WifiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WifiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
		mWifiCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
		mWifiCircleGapWidth = 1.5f * mWifiCircleWidth;
		mCenterPointF = new PointF();
		mWifiPointF = new PointF();
		mWifiRectF = new RectF();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			width = defaultWidth;
		}
		if (heightMode != MeasureSpec.EXACTLY) {
			height = defaultHeight;
		}
		int size = Math.min(width, height);
		setMeasuredDimension(size, size);
		isPointsDirty = true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isPointsDirty) {
			initPoints();
		}
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mCircleWidth);
		canvas.drawCircle(mCenterPointF.x, mCenterPointF.y, getWidth() / 2 - mCircleWidth, mPaint);
		
		mPaint.setStrokeWidth(mWifiCircleWidth);
		float radius = 0;
		for (int i = 0; i < 5; i++) {
			radius = (i + 1) * mWifiCircleWidth + i * mWifiCircleGapWidth - mWifiCircleWidth / 2;
			mWifiRectF.set(mWifiPointF.x - radius
					, mWifiPointF.y - radius
					, mWifiPointF.x + radius
					, mWifiPointF.y + radius);
			int progress = (int) (mProgress * 5);
			mPaint.setColor(i <= progress ? Color.GREEN : Color.LTGRAY);
			canvas.drawArc(mWifiRectF, 225, 90, false, mPaint);
		}
	}

	private void initPoints() {
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		mCenterPointF.set(measuredWidth / 2, measuredHeight / 2);
		mWifiPointF.set(measuredWidth / 2, measuredHeight * 0.75f);
	}
	
	private ValueAnimator mValueAnimator ;
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mValueAnimator = ValueAnimator.ofFloat(0f,1.0f);
		mValueAnimator.setDuration(5000);
		mValueAnimator.setRepeatCount(Animation.INFINITE);
		mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mProgress = (float) animation.getAnimatedValue();
				invalidate();
			}
		});
		mValueAnimator.start();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		if (mValueAnimator != null) {
			mValueAnimator.cancel();
		}
		super.onDetachedFromWindow();
	}
	
	
	public void setProgress(float progress) {
		mProgress = progress;
		invalidate();
		
	}
}
