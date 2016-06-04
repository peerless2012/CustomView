package com.peerless2012.customerview.view;

import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
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
	
	private Random mRandom = new Random();
	
	private final static float PROPORATION = 1.2f;
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private float defaultWidth;
	
	/**
	 * View缺省高度
	 */
	private float defaultHeight;
	
	private int mWidth;
	
	private int mHeight;
	
	private boolean isDirty = true;
	
	private RectF tempRect = new RectF();
	
	private Picture mBgPicture;
	
	// 虚线
	
	private float mDashHeight;
	
	private float mDashLength;
	
	private float mDashSpaceLength;
	
	private float mDashMagin;
	
	private DashPathEffect mDashPathEffect;
	
	
	// 字体大小
	
	private float mTextSize50;
	
	private float mTextSize22;
	
	private float mTextSize18;
	
	private float mTextSize16;
	
	private float mTextSize14;
	
	// 颜色
	
	private int mBgTopColor;
	
	private int mBgBottomColor;
	
	private int mLightBlueColor;
	
	private int mWhiteColor;
	
	private int mGrayColor;
	
	// 百分比
	private float mDividerPercent = 0.85f;
	
	private float mDashLinePercent = 0.67f;
	
	// 绘制内容区
	
	private int[] mCurrentColors = {0xFF9A9BF8,0xFF9AA2F7, 0xFF65CCD1,0xFF63D0CD,0xFF68CBD0,0xFF999AF6,0xFF9A9BF8};
	
	private float[] mCurrentPoints = {0,1f/6,2f/6,3f/6,4f/6,5f/6,1};
	
	private float mProgressWidth;
	
	// 边距
	
	private float mMargin6;
	
	private float mMargin10;
	
	private float mMargin13;
	
	public QQHealthView(Context context) {
		this(context,null);
	}
	
	public QQHealthView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		defaultWidth = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 100);
		defaultHeight = PROPORATION * defaultWidth;
		
		mProgressWidth = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		mDashHeight = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 2);
		mDashLength = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 5);
		mDashSpaceLength = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 2);
		mDashMagin = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		mDashPathEffect = new DashPathEffect(new float[]{mDashLength,mDashSpaceLength}, 0);
		
		mTextSize50 = generateScaledSize(TypedValue.COMPLEX_UNIT_SP, 50);
		mTextSize22 = generateScaledSize(TypedValue.COMPLEX_UNIT_SP, 22);
		mTextSize18 = generateScaledSize(TypedValue.COMPLEX_UNIT_SP, 18);
		mTextSize16 = generateScaledSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mTextSize14 = generateScaledSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mMargin6 = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 6);
		mMargin10 = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 10);
		mMargin13 = generateScaledSize(TypedValue.COMPLEX_UNIT_DIP, 13);
		
		// 处理颜色
		mBgTopColor = Color.parseColor("#4C5A67");
		mBgBottomColor = Color.parseColor("#496980");
		mLightBlueColor = Color.parseColor("#63CFEC");
		mWhiteColor = Color.WHITE;
		mGrayColor = Color.parseColor("#808C96");
		
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
		
		drawRecently(canvas);
		
		drawButtomBar(canvas);
	}
	
	/**
	 * 重置参数
	 */
	private void preDraw() {
		mPaint.reset();
		mPaint.setAntiAlias(true);// 重置以后不要忘了重新设置抗锯齿，否则无抗锯齿功能
		mTextPaint.reset();
		mTextPaint.setAntiAlias(true);
	}
	
	/**
	 * 绘制背景
	 * @param canvas
	 */
	private void drawBg(Canvas canvas) {
		// 如果需要重新创建Picture
		if (isDirty) {
			
			// 开始用Picture录制
			mBgPicture = new Picture();
			Canvas bgCanvas = mBgPicture.beginRecording(mWidth, mHeight);
			
			// 上部占高度 0.85，下部占0.15
			float yDivider = mHeight * mDividerPercent;
			
			// 绘制上面背景
			mPaint.setColor(mBgTopColor);
			bgCanvas.drawRect(0, 0, mWidth, yDivider, mPaint);
			
			// 绘制下面背景
			mPaint.setColor(mBgBottomColor);
			bgCanvas.drawRect(0, yDivider, mWidth, mHeight, mPaint);
			
			// 绘制虚线
			mPaint.setColor(mGrayColor);
			mPaint.setStrokeWidth(mDashHeight);
			float yDash = mHeight * mDashLinePercent;
			mPaint.setPathEffect(mDashPathEffect);
			bgCanvas.drawLine(mDashMagin, yDash, mWidth - mDashMagin, yDash, mPaint);
			mPaint.setPathEffect(null);
			
			// 裁剪四角
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
			bgCanvas.drawPath(getClipPath(), mPaint);
			mPaint.setXfermode(null);
			
			// 结束录制
			mBgPicture.endRecording();
		}
		
		// 绘制录制的内容到Canvas
		mBgPicture.draw(canvas);
	}
	
	/**
	 * 绘制最近7天进度
	 * @param canvas
	 */
	private void drawRecently(Canvas canvas) {
		
		// 绘制虚线上的文字
		mTextPaint.setColor(mGrayColor);
		mTextPaint.setTextSize(mTextSize16);
		mTextPaint.setTextAlign(Align.LEFT);
		canvas.drawText("最近7天", mDashMagin, mDashLinePercent * mHeight - mMargin13, mTextPaint);
		
		mTextPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("平均9977步/天", mWidth - mDashMagin, mDashLinePercent * mHeight - mMargin13, mTextPaint);
		
		// 绘制柱状图和文字
		float itemLength = (mWidth - mDashMagin * 2) / 7;
		float startX = 0;
		float startY = 0;
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(mTextSize16);
		mTextPaint.setTextAlign(Align.CENTER);
		mPaint.setStyle(Style.FILL);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setStrokeWidth(mProgressWidth);
		mPaint.setColor(mLightBlueColor);
		float textHeight = getTextHeight(mTextPaint);
		float textY = mDividerPercent * mHeight - mMargin13;
		startY = textY - textHeight - mMargin13;
		for (int i = 0; i < 7; i++) {
			startX = mDashMagin + itemLength / 2 + i * itemLength;
			canvas.drawText(i +"日", startX, textY, mTextPaint);
			canvas.drawLine(startX, startY, startX, startY - (mRandom.nextInt(25)+ 25), mPaint);
		}
		
	}

	

	/**
	 * 绘制今天的进度
	 * @param canvas
	 */
	private void drawCircle(Canvas canvas) {
		// 确定圆心及半径
		float circleRadius = getHeight() * 0.5f * 0.5f;
		float centerX = getWidth() /2;
		float centerY = circleRadius + mDashMagin + mProgressWidth / 2;
		
		// 绘制圆环
		SweepGradient sweepGradient = new SweepGradient(centerX, centerY, mCurrentColors , mCurrentPoints);
		tempRect.set(centerX - circleRadius, centerY - circleRadius, centerX + circleRadius, centerY + circleRadius);
		mPaint.setStrokeWidth(mProgressWidth);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setShader(sweepGradient);
		canvas.drawArc(tempRect, 120, 300, false, mPaint);
		mPaint.setShader(null);
		
		// 绘制圆环内文字
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setTextSize(mTextSize50);
		mTextPaint.setColor(mWhiteColor);
		float centerTextHeight = getTextHeight(mTextPaint);
		canvas.drawText("5599",centerX , centerY + getTextYOffset(mTextPaint), mTextPaint);
		
		// 绘制圆环内数字上下的文字
		mTextPaint.setTextSize(mTextSize18);
		mTextPaint.setColor(mGrayColor);
//		float currentTextHeight = getTextHeight(mTextPaint);
		canvas.drawText("截止22:34已走", centerX, centerY - centerTextHeight / 2 - mMargin10, mTextPaint);
		canvas.drawText("好友平均239步", centerX, centerY + centerTextHeight / 2 + mMargin10, mTextPaint);
		
		// 绘制圆环下文字
		mTextPaint.setTextSize(mTextSize22);
		mTextPaint.setColor(mWhiteColor);
		float textWidth = mTextPaint.measureText("67");
		float bottomTextY = centerY + circleRadius + mProgressWidth + getTextYOffset(mTextPaint);
		canvas.drawText("67", centerX, bottomTextY , mTextPaint);
		mTextPaint.setTextAlign(Align.RIGHT);
		mTextPaint.setTextSize(mTextSize18);
		canvas.drawText("第", centerX - mMargin10 - textWidth / 2, bottomTextY , mTextPaint);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setTextSize(mTextSize18);
		canvas.drawText("天", centerX + mMargin10 + textWidth / 2, bottomTextY , mTextPaint);
	}
	
	/**
	 * 绘制底部
	 * @param canvas
	 */
	private void drawButtomBar(Canvas canvas) {
		
		mTextPaint.setColor(mWhiteColor);
		mTextPaint.setTextSize(mTextSize18);
		float textY = 0.925f * mHeight + getTextYOffset(mTextPaint);
		
		// 左侧
		mTextPaint.setTextAlign(Align.LEFT);
		canvas.drawText("这是被隐藏的内容", mDashMagin , textY , mTextPaint);
		
		// 右侧
		mTextPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("查看 > ", mWidth - mDashMagin , textY , mTextPaint);
	}
	
	/**
	 * 获取绘制文字的高度
	 * @param paint 画笔
	 * @return 文字高度
	 */
	private float getTextHeight(Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		return fontMetrics.bottom - fontMetrics.top;
	}
	
	/**
	 * 根据相应的类型和值转化为缩放后的值（dp、sp等到px）
	 * @param unit 类型
	 * @param value 值
	 * @return 转化后的值
	 */
	private float generateScaledSize(int unit, float value) {
		return TypedValue.applyDimension(unit, value, getResources().getDisplayMetrics());
	}
	
	/**
	 * 获取绘制文字在指定坐标中心时候y方向上应有的偏移值
	 * @param paint 画笔
	 * @return y的偏移值
	 */
	private float getTextYOffset(Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		return (fontMetrics.descent - fontMetrics.ascent) / 4;
	}
	

	/*-----------------------------------------裁剪四角代码区域，可通用-------------------------------------*/
	
	/********************************************************裁剪圆角方法区***********************************************************/
	
	private RectF mClipBgRectF = new RectF();
	private RectF mClipCornerRectF = new RectF();
	private float radius[] = new float[]{
			10,10,10,10,10,10,10,10
	};
	
	private Path getClipPath() {
		mClipBgRectF.set(
				  0 + getPaddingLeft()
				, 0 + getPaddingTop()
				, getWidth()  - getPaddingRight()
				, getHeight() - getPaddingBottom());
		Path clipPath =new Path();
		
		//左上角
		clipPath.moveTo(mClipBgRectF.left, mClipBgRectF.top);
		clipPath.lineTo(mClipBgRectF.left + radius[0], mClipBgRectF.top);
		mClipCornerRectF.set(
				  mClipBgRectF.left
				, mClipBgRectF.top
				, mClipBgRectF.left + radius[0] * 2
				, mClipBgRectF.top + radius[1] * 2);
		clipPath.arcTo(mClipCornerRectF, 270, -90);
		clipPath.close();
		
		//右上角
		clipPath.moveTo(mClipBgRectF.right, mClipBgRectF.top);
		clipPath.lineTo(mClipBgRectF.right, mClipBgRectF.top + radius[3]);
		mClipCornerRectF.set(
				  mClipBgRectF.right - radius[2] * 2
				, mClipBgRectF.top, mClipBgRectF.right
				, mClipBgRectF.top + radius[3] * 2);
		clipPath.arcTo(mClipCornerRectF, 0, -90);
		clipPath.close();
		
		//右下角
		clipPath.moveTo(mClipBgRectF.right, mClipBgRectF.bottom);
		clipPath.lineTo(mClipBgRectF.right - radius[4], mClipBgRectF.bottom);
		mClipCornerRectF.set(
				  mClipBgRectF.right - radius[4] * 2
				, mClipBgRectF.bottom - radius[5] * 2
				, mClipBgRectF.right, mClipBgRectF.bottom);
		clipPath.arcTo(mClipCornerRectF, 90, -90);
		clipPath.close();
		
		//左下角
		clipPath.moveTo(mClipBgRectF.left, mClipBgRectF.bottom);
		clipPath.lineTo(mClipBgRectF.left, mClipBgRectF.bottom - radius[7]);
		mClipCornerRectF.set(
				  mClipBgRectF.left
				, mClipBgRectF.bottom - radius[7] * 2
				, mClipBgRectF.left + radius[6] * 2
				, mClipBgRectF.bottom);
		clipPath.arcTo(mClipCornerRectF, 180, -90);
		clipPath.close();
		
		return clipPath;
	}
	
}
