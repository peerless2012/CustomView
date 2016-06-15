package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月15日 下午3:42:32
* @Version V1.0
* @Description: 少数派的自定义logo
*/
public class SSPaiView extends View {
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private int defaultWidth;
	
	/**
	 * View缺省高度
	 */
	private int defaultHeight;
	
	private boolean isPointsDirty = false;
	
	private int mContentWidth = 0;
	
	private int centerX;
	
	private int centerY;
	
	private int radius;
	
	private int edgeWidth = 2;
	
	public SSPaiView(Context context) {
		super(context);
		init(context,null);
	}

	public SSPaiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}
	
	public SSPaiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		edgeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
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
		radius = mContentWidth / 2 - edgeWidth;
		
		isPointsDirty = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (isPointsDirty) {
			initPath();
			isPointsDirty = false;
		}
		
		canvas.translate(centerX, centerY);
		
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(edgeWidth);
		canvas.drawCircle(0, 0, radius, mPaint);
	}

	private void initPath() {
		
	}

	
}
