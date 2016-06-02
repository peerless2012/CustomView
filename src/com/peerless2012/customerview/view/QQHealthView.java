package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
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
	
	private int mWidth;
	
	private int mHeight;
	
	private boolean isDirty = true;
	
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
		
		setLayerType(LAYER_TYPE_SOFTWARE, mPaint);//使用Picture的话需要不开启硬件加速
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode == MeasureSpec.UNSPECIFIED) {
			mWidth = (int) defaultWidth;
		}
		mHeight = (int) (mWidth * PROPORATION);
		setMeasuredDimension(mWidth, mHeight);
		isDirty = true;
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		preDraw();
		
		drawBg(canvas);
		
		drawCircle(canvas);
	}
	
	private void preDraw() {
		mPaint.reset();
	}

	private void drawCircle(Canvas canvas) {
		float circleRadius = getHeight() * 0.5f * 0.5f;
		float centerX = getWidth() /2;
		float centerY = circleRadius + mDashMagin;
		
		mPaint.reset();
		int[] colors = {0xFF9A9BF8,0xFF9AA2F7, 0xFF65CCD1,0xFF63D0CD,0xFF68CBD0,0xFF999AF6,0xFF9A9BF8};
        float[] positions = {0,1f/6,2f/6,3f/6,4f/6,5f/6,1};
        SweepGradient mSweepGradient = new SweepGradient(centerX, centerY, colors , positions);
		
		tempRect.set(centerX - circleRadius, centerY - circleRadius, centerX + circleRadius, centerY + circleRadius);
		mPaint.setStrokeWidth(20);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setShader(mSweepGradient);
		canvas.drawArc(tempRect, 120, 300, false, mPaint);
		mPaint.setShader(null);
	}
	
	private Picture mBgPicture;
	
	private void drawBg(Canvas canvas) {
		if (isDirty) {
			bgRect = new RectF(0, 0, mWidth, mHeight);
			
			mBgPicture = new Picture();
			Canvas bgCanvas = mBgPicture.beginRecording(mWidth, mHeight);
			
//			bgCanvas.saveLayer(bgRect, mPaint, Canvas.ALL_SAVE_FLAG);
			
			float yDivider = mHeight * 0.85f;
			// 上部占高度 0.85，下部占0.15
			// 绘制上面背景
			mPaint.setColor(Color.parseColor("#4c5a67"));
			bgCanvas.drawRect(0, 0, mWidth, yDivider, mPaint);
			
			// 绘制下面背景
			mPaint.setColor(Color.parseColor("#496980"));
			bgCanvas.drawRect(0, yDivider, mWidth, mHeight, mPaint);
			
			// 绘制虚线
			mPaint.setColor(mColorGray);
			mPaint.setStrokeWidth(5);
			float yDash = mHeight * 0.67f;
			mPaint.setPathEffect(new DashPathEffect(new float[]{mDashLength,mDashSpaceLength}, 0));
			bgCanvas.drawLine(mDashMagin, yDash, mWidth - mDashMagin, yDash, mPaint);
			mPaint.setPathEffect(null);
			
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
			bgCanvas.drawPath(getClipPath(), mPaint);
			
			mBgPicture.endRecording();
//			bgCanvas.restore();
		}else {
			
		}
		mBgPicture.draw(canvas);
	}
	
	
	
	private float radius[] = new float[]{
			20,20,20,20,20,20,20,20
	};

	private RectF bgRect;
	private RectF tempRect = new RectF();
	private Path getClipPath() {
		Rect bgRect = new Rect(0 + getPaddingLeft()
				, 0 + getPaddingTop()
				, getWidth() - getPaddingRight()
				, getHeight() - getPaddingBottom());
		RectF rectF = new RectF();
		Path clipPath =new Path();
		
		//左上角
		clipPath.moveTo(bgRect.left, bgRect.top);
		clipPath.lineTo(bgRect.left + radius[0], bgRect.top);
		rectF.set(bgRect.left, bgRect.top, bgRect.left + radius[0] * 2, bgRect.top + radius[1] * 2);
		clipPath.arcTo(rectF, 270, -90);
		clipPath.close();
		
		//右上角
		clipPath.moveTo(bgRect.right, bgRect.top);
		clipPath.lineTo(bgRect.right, bgRect.top + radius[3]);
		rectF.set(bgRect.right - radius[2] * 2, bgRect.top, bgRect.right, bgRect.top + radius[3] * 2);
		clipPath.arcTo(rectF, 0, -90);
		clipPath.close();
		
		//右下角
		clipPath.moveTo(bgRect.right, bgRect.bottom);
		clipPath.lineTo(bgRect.right - radius[4], bgRect.bottom);
		rectF.set(bgRect.right - radius[4] * 2, bgRect.bottom - radius[5] * 2, bgRect.right, bgRect.bottom);
		clipPath.arcTo(rectF, 90, -90);
		clipPath.close();
		
		//左下角
		clipPath.moveTo(bgRect.left, bgRect.bottom);
		clipPath.lineTo(bgRect.left, bgRect.bottom - radius[7]);
		rectF.set(bgRect.left, bgRect.bottom - radius[7] * 2, bgRect.left + radius[6] * 2, bgRect.bottom);
		clipPath.arcTo(rectF, 180, -90);
		clipPath.close();
		
		return clipPath;
	}
	
}
