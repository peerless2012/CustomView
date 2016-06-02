package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月2日 下午11:12:24
* @Version V1.0
* @Description: QQ健康
*/
public class QQHealthView extends View {
	
	private final static float PROPORATION = 1.2f;
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private float defaultWidth;
	
	/**
	 * View缺省高度
	 */
	private float defaultHeight;
	
	private int mColorGray;
	
	private int mColorWhite;
	
	private float mDashLength;
	
	private float mDashSpaceLength;
	
	private float mDashMagin;
	
	public QQHealthView(Context context) {
		this(context,null);
	}
	
	public QQHealthView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		defaultWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
		defaultHeight = PROPORATION * defaultWidth;
		
		mDashLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
		mDashSpaceLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
		mDashMagin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
		
		mColorWhite = Color.WHITE;
		mColorGray = Color.parseColor("#76828E");
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode == MeasureSpec.UNSPECIFIED) {
			width = (int) defaultWidth;
		}
		int height = (int) (width * PROPORATION);
		setMeasuredDimension(width, height);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		drawBg(canvas);
	}
	
	private void drawBg(Canvas canvas) {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		float yDivider = height * 0.85f;
		// 上部占高度 0.85，下部占0.15
		// 绘制上面背景
		mPaint.setColor(Color.parseColor("#4c5a67"));
		canvas.drawRect(0, 0, width, yDivider, mPaint);
		
		// 绘制下面背景
		mPaint.setColor(Color.parseColor("#496980"));
		canvas.drawRect(0, yDivider, width, height, mPaint);
		
		// 绘制虚线
		mPaint.setColor(mColorGray);
		mPaint.setStrokeWidth(5);
		float yDash = height * 0.67f;
		mPaint.setPathEffect(new DashPathEffect(new float[]{mDashLength,mDashSpaceLength}, 0));
		canvas.drawLine(mDashMagin, yDash, width - mDashMagin, yDash, mPaint);
		mPaint.setPathEffect(null);
	}
	
}
