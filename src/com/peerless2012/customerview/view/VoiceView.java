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
import android.graphics.Picture;
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
	
	/**
	 * 点线其实是把View划分成9 x 9的网格，它是每个格子的大小
	 */
	private float mItemSize;
	
	/**
	 * 话筒外部区域的rect
	 */
	private RectF mRectFOuter;
	
	/**
	 * 话筒内部的rect
	 */
	private RectF mRectFInner;
	
	/**
	 * 当前的进度
	 */
	private float mProgress = 0.8f;
	
	/**
	 * 绘制的路径
	 */
	private Path mPath;
	
	private Picture mPicture = new Picture();
	
	private float mRadius;
	
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
		
		//只要是不是精确指定的，就设置为默认宽高。
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
	
	
	
	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mRectFInner = new RectF();
		mRectFOuter = new RectF();
		mPath = new Path();
		
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBg(canvas);
		
		//裁剪需要绘制绿色的区域
		canvas.clipRect(mRectFInner.left, mRectFInner.top + (mRectFInner.bottom - mRectFInner.top) * ( 1- mProgress)
				, mRectFInner.right, mRectFInner.bottom);
		
		//绘制绿色话筒
		mPaint.setColor(Color.GREEN);
		canvas.drawRoundRect(mRectFInner, mRadius, mRadius, mPaint);
	}

	private void drawBg(Canvas canvas) {
		if (isPointsDirty) {
			initPoints();
			
			mRadius = 3.6f * mItemSize;
			
			Canvas recordingCanvas = mPicture.beginRecording(getMeasuredWidth(), getMeasuredHeight());
			
			//设置颜色为浅灰色
			mPaint.setColor(Color.LTGRAY);
			//设置Paint的样式为空心
			mPaint.setStyle(Style.STROKE);
			//设置Paint的笔帽为圆形
			mPaint.setStrokeCap(Cap.ROUND);
			// 设置Paint宽度
			mPaint.setStrokeWidth(mItemSize / 2);
			
			//绘制路径
			recordingCanvas.drawPath(mPath, mPaint);
			
			mPaint.setStyle(Style.FILL);
			//绘制话筒内部（灰色区域）
			recordingCanvas.drawRoundRect(mRectFInner, mRadius, mRadius, mPaint);
			
			mPicture.endRecording();
			
			isPointsDirty = false;
		}
		
		mPicture.draw(canvas);
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
			mValueAnimator.end();;
		}
		super.onDetachedFromWindow();
	}
	
	/**
	 * 设置当前进度
	 * @param progress 最新进度
	 */
	public void setProgress(float progress) {
		mProgress = progress;
		invalidate();
		
	}
}
