package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.SweepGradient;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016年7月25日 下午3:12:36
* @Version V1.0
* @Description: 支付宝信用
*/
public class CreditView extends View {
	private final static String [] mTickMarkTexts = {
		"350","较差","550","中等","600","良好","650","优秀","700","极好","950"
	};
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private int mGrayWhiteColor;
	
	private int mWhiteColor;
	
	private RectF mCircleRectF = new RectF();
	private RectF mSurroundCircleRectF = new RectF();
	
	/**
	 * 中心点
	 */
	private float mCenterX,mCenterY;
	
	/**
	 * 半径
	 */
	private float mRadius;
	
	private float mTickMarkWidth = 10;
	
	private float mTickMarkLineShortWidth = 2;
	
	private float mTickMarkLineLongWidth = 5;
	
	private int[] mCurrentColors = {0x66FFFFFF,0xFFFFFFFF};
	
	private float[] mCurrentPoints = {0,1};
	
	private float mRollBackAngel =  90 + 7.5f * 3;
	
	private float mStartAngel = 157.5f;
	
	private float mSweepAngel = 225f;
	
	private float mProgress = 0.8f;
	
	
	public CreditView(Context context) {
		this(context,null);
	}

	public CreditView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public CreditView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setWillNotDraw(false);
		mGrayWhiteColor = Color.parseColor("#66FFFFFF");
		mWhiteColor = Color.parseColor("#AAFFFFFF");
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int height = (int) (width * 1.5f);
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mCenterX = w / 2;
		mCenterY = h / 2;
		mRadius = w / 3;
		float circleOffset = mTickMarkWidth / 2;
		
		mCircleRectF.set(-mRadius + circleOffset, -mRadius + circleOffset, mRadius - circleOffset, mRadius - circleOffset);
		mSurroundCircleRectF.set(-mRadius - circleOffset * 3, -mRadius - circleOffset * 3, mRadius + circleOffset * 3, mRadius + circleOffset * 3);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(mCenterX, mCenterY);
		canvas.drawColor(Color.BLUE);
		drawTickMark(canvas);
		drawSurround(canvas);
		drawCreditText(canvas);
	}
	
	
	private void drawCreditText(Canvas canvas) {
		mTextPaint.setTextSize(40);
		float evaluateHeight = getTextHeight(mTextPaint);
		canvas.drawText("信用极好", 0, 0 + getTextYOffset(mTextPaint), mTextPaint);
		
		mTextPaint.setTextSize(60);
		float scoreHeight = getTextHeight(mTextPaint);
		canvas.drawText("749", 0, 0 + getTextYOffset(mTextPaint) - evaluateHeight / 2 - scoreHeight / 2, mTextPaint);
		
		mTextPaint.setTextSize(20);
		float timeHeight = getTextHeight(mTextPaint);
		canvas.drawText("评估时间：2016.07.23", 0, 0 + timeHeight + evaluateHeight / 2 + getTextYOffset(mTextPaint), mTextPaint);
		
	}

	private void drawSurround(Canvas canvas) {
		mPaint.setColor(mGrayWhiteColor);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.STROKE);
		canvas.drawArc(mSurroundCircleRectF, mStartAngel, mSweepAngel, false, mPaint);
		SweepGradient sweepGradient = new SweepGradient(0, 0, mCurrentColors , mCurrentPoints);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setShader(sweepGradient);
		canvas.drawArc(mSurroundCircleRectF, mStartAngel, mSweepAngel * mProgress, false, mPaint);
		mPaint.setShader(null);
		mPaint.setStrokeCap(Cap.BUTT);
		
		// 绘制模糊点
		float rotate = -mRollBackAngel + mProgress * mSweepAngel;
		canvas.save();
		canvas.rotate(rotate);
		mPaint.setStyle(Style.FILL);
		float circleOffset = mTickMarkWidth / 2;
		canvas.drawCircle(0, -mRadius - circleOffset * 3, 10, mPaint);
		canvas.restore();
	}

	/**
	 * 绘制刻度（一刻度=7.5度）
	 * 角度从 
	 * @param canvas
	 */
	private void drawTickMark(Canvas canvas) {
		mPaint.setColor(mGrayWhiteColor);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Style.STROKE);
		canvas.drawArc(mCircleRectF, mStartAngel, mSweepAngel, false, mPaint);
		
		canvas.save();
		mPaint.setColor(mWhiteColor);
		canvas.rotate(mRollBackAngel);
		mPaint.setStrokeWidth(2);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(16);
		for (int i = 0; i < 30; i++) {
			if (i % 6 ==0) {
				mPaint.setStrokeWidth(mTickMarkLineLongWidth);
			}else {
				mPaint.setStrokeWidth(mTickMarkLineShortWidth);
			}
			canvas.drawLine(0, -mRadius, 0, -mRadius + mTickMarkWidth, mPaint);
			if (i % 3 == 0) {
				canvas.drawText(mTickMarkTexts[i / 3], 0, -mRadius + mTickMarkWidth * 4, mTextPaint);
			}
			canvas.rotate(-7.5f);
		}
		mPaint.setStrokeWidth(mTickMarkLineLongWidth);
		canvas.drawLine(0, -mRadius, 0, -mRadius + mTickMarkWidth, mPaint);
		canvas.drawText(mTickMarkTexts[mTickMarkTexts.length - 1], 0, -mRadius + mTickMarkWidth * 4, mTextPaint);
		canvas.restore();
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
}
