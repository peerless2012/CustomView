package com.peerless2012.customerview.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年5月30日 下午10:58:13
* @Version V1.0
* @Description: 自定义话筒录音的View （drawable下voice）
*/
public class VoiceView extends View {
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private int defaultWidth;
	
	private int defaultHeight;
	
	private boolean isPointDirty = false;
	
	private float mItemSize;
	
	private RectF mRectFOuter;
	
	private RectF mRectFInner;
	
	private float mProgress = 0.8f;
	
	private Path mPath;
	
	public VoiceView(Context context) {
		this(context, null);
	}

	public VoiceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
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
		setMeasuredDimension(width, height);
		isPointDirty = true;
	}
	
	
	
	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mRectFInner = new RectF();
		mRectFOuter = new RectF();
		mPath = new Path();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isPointDirty) {
			initPoints();
		}
		
		mPaint.setColor(Color.LTGRAY);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setStrokeWidth(mItemSize / 2);
		
		canvas.drawPath(mPath, mPaint);
		
		mPaint.setStyle(Style.FILL);
		float r = 3.6f * mItemSize;
		canvas.drawRoundRect(mRectFInner, r, r, mPaint);
		
		canvas.clipRect(mRectFInner.left, mRectFInner.top + (mRectFInner.bottom - mRectFInner.top) * ( 1- mProgress)
				, mRectFInner.right, mRectFInner.bottom);
		mPaint.setColor(Color.GREEN);
		canvas.drawRoundRect(mRectFInner, r, r, mPaint);
	}

	private void initPoints() {
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		float itemHeight = measuredHeight / 9.0f;
		float itemWidth = measuredWidth / 9.0f;
		mItemSize = itemHeight;
		
		mRectFInner.set(2.7f * itemWidth, 0.3f * itemHeight, 6.3f * itemWidth, 6.7f * itemHeight);
		mRectFOuter.set(2.0f * itemWidth, 2.5f * itemHeight, 7.0f * itemWidth, 7.5f * itemHeight);
		
		// 底部线
		mPath.moveTo(1.5f * itemWidth, 8.5f * itemHeight);
		mPath.lineTo(7.5f * itemWidth, 8.5f * itemHeight);
		
		//连接底部和上部的线
		mPath.moveTo(4.5f * itemWidth, 8.5f * itemHeight);
		mPath.lineTo(4.5f * itemWidth, 7.5f * itemHeight);
		
		// 上部左侧直线
		mPath.moveTo(2.0f * itemWidth, 3.5f * itemHeight);
		mPath.lineTo(2.0f * itemWidth, 5.0f * itemHeight);
		
		// 上部左下1/4圆
		mPath.addArc(mRectFOuter, 90, 90);
		
		// 上部右下1/4圆
		mPath.addArc(mRectFOuter, 0, 90);
		
		// 上部右侧直线
		mPath.moveTo(7.0f * itemWidth, 5f * itemHeight);
		mPath.lineTo(7.0f * itemWidth, 3.5f * itemHeight);
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
