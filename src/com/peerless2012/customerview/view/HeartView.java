package com.peerless2012.customerview.view;

import com.peerless2012.customerview.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;

public class HeartView extends View {

	 // width="645"
	 // height="585"
	
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
	
	private Path mHeartPath = new Path();
	
	private Path mCirclePath = new Path();
	
	private Bitmap mBitmap;
	
	public HeartView(Context context) {
		super(context);
		init(context, null);
	}

	public HeartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, null);
	}
	
	
	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putian);
		setWillNotDraw(false);
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
	}
	
	private LinearGradient linearGradient;
	
	private Matrix mMatrix = new Matrix();
	
	private Matrix mBitmapMatrix = new Matrix();
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(getWidth() / 2, getHeight() / 2);
		if (isPointsDirty) {
			initData();
			isPointsDirty = false;
		}
		int width = getWidth() / 2;
		
		
		
		if (progress <= -2) {
			float p = progress + 3;
			mPaint.setColor(Color.RED);
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p * 1.5f, Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawPath(mHeartPath, mPaint);
		}else if (progress <= 1){
			float startX = width * progress;
			float startY = startX;
			if (linearGradient == null) {
				linearGradient = new LinearGradient(0,0,width,width, Color.BLACK, Color.RED, TileMode.CLAMP);
			}
			linearGradient.getLocalMatrix(mMatrix);
			mMatrix.setTranslate(startX, startY);
			linearGradient.setLocalMatrix(mMatrix);

			mPaint.setShader(linearGradient);
			canvas.drawPath(mHeartPath, mPaint);
			mPaint.setShader(null);
		}else if (progress <= 2) {
			float p = 1 - (progress - 1);
			mPaint.setColor(Color.BLACK);
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p, Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawPath(mHeartPath, mPaint);
		}else {
			float p = progress - 2;
			mCirclePath.reset();
			mCirclePath.addCircle(0, 0, width * p * 1.5f, Direction.CCW);
			canvas.clipPath(mCirclePath);
			canvas.drawBitmap(mBitmap, mBitmapMatrix, mPaint);
		}
		
	}

	/*private void initData() {
		// 得到屏幕的长宽的一半  
        int px = getMeasuredWidth() / 2;  
        int py = getMeasuredHeight() / 2;  
        // 路径的起始点  
        mHeartPath.moveTo(px, py - 5 * rate);  
        // 根据心形函数画图  
        for (double i = 0; i <= 2 * Math.PI; i += 0.001) {  
            float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));  
            float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));  
            x *= rate;  
            y *= rate;  
            x = px - x;  
            y = py - y;  
            mHeartPath.lineTo(x, y);  
        }  
        
	}*/
	
	// 645 322.5
	// 585 
	private void initData() {
		
		int width = getMeasuredWidth() / 2;
		
		mHeartPath.reset();
		
		mHeartPath.moveTo(-0.078147426f * width ,0.7081185f * width );
		mHeartPath.cubicTo(-0.12086068f * width ,0.6602539f * width ,-0.22751379f * width ,0.56694126f * width ,-0.31515422f * width ,0.5007569f * width );
		mHeartPath.cubicTo(-0.5748258f * width ,0.30465826f * width ,-0.6101672f * width ,0.2762788f * width ,-0.71559924f * width ,0.17919657f * width );
		mHeartPath.cubicTo(-0.9099699f * width ,2.1925342E-4f * width ,-0.99251586f * width ,-0.1795942f * width ,-0.99223304f * width ,-0.42340702f * width );
		mHeartPath.cubicTo(-0.99209505f * width ,-0.5424267f * width ,-0.98398256f * width ,-0.5882813f * width ,-0.950652f * width ,-0.6584373f * width );
		mHeartPath.cubicTo(-0.8941041f * width ,-0.7774627f * width ,-0.8108063f * width ,-0.865908f * width ,-0.70430994f * width ,-0.9200016f * width );
		mHeartPath.cubicTo(-0.6288823f * width ,-0.95831424f * width ,-0.5916816f * width ,-0.97533584f * width ,-0.46572256f * width ,-0.9760322f * width );
		mHeartPath.cubicTo(-0.33396038f * width ,-0.9767604f * width ,-0.30622295f * width ,-0.9613974f * width ,-0.22871567f * width ,-0.91881716f * width );
		mHeartPath.cubicTo(-0.13437614f * width ,-0.86698985f * width ,-0.037274122f * width ,-0.7562259f * width ,-0.017201789f * width ,-0.67754406f * width );
		mHeartPath.lineTo(-0.004804555f * width ,-0.62894744f * width );
		mHeartPath.lineTo(0.025767908f * width ,-0.6958762f * width );
		mHeartPath.cubicTo(0.19853118f * width ,-1.0740868f * width ,0.7501039f * width ,-1.0684283f * width ,0.9420541f * width ,-0.6864759f * width );
		mHeartPath.cubicTo(1.0029461f * width ,-0.5653098f * width ,1.0096313f * width ,-0.30659008f * width ,0.95563585f * width ,-0.16085461f * width );
		mHeartPath.cubicTo(0.8852009f * width ,0.02925202f * width ,0.75290716f * width ,0.17418581f * width ,0.44708905f * width ,0.39628217f * width );
		mHeartPath.cubicTo(0.24652837f * width ,0.5419368f * width ,0.019539483f * width ,0.76231587f * width ,0.0037380958f * width ,0.79326814f * width );
		mHeartPath.cubicTo(-0.014604435f * width ,0.82919866f * width ,0.0028632586f * width ,0.79889894f * width ,-0.078147426f * width ,0.7081185f * width );

		
		mHeartPath.close();
		
//		float scaleX = getWidth() / ( bitmapWidth * 1.0f);
//		float scaleY = getHeight() / (bitmapHeight * 1.0f);
//		float scale = Math.max(scaleX, scaleY);
		mBitmapMatrix.reset();
//		mBitmapMatrix.setTranslate(-getWidth() / 2, -getHeight() / 2);
//		mBitmapMatrix.postScale(scale,scale);
		
		int dwidth = mBitmap.getWidth();
        int dheight = mBitmap.getHeight();

        int vwidth = getWidth();
        int vheight = getHeight();
		
        float scale;
        float dx;
        float dy;
        
        if (dwidth <= vwidth && dheight <= vheight) {
            scale = 1.0f;
        } else {
            scale = Math.min((float) vwidth / (float) dwidth,
                    (float) vheight / (float) dheight);
        }
        
        dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
        dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);
        mBitmapMatrix.setScale(scale, scale);
        mBitmapMatrix.postTranslate(dx - getWidth() / 2, dy - getHeight() / 2);
		
	}
/*	private void initData() {
		
		mHeartPath.reset();
		
		mHeartPath.moveTo(297.29747f,550.86823f);
		mHeartPath.cubicTo(283.52243f,535.43191f,249.1268f,505.33855f, 220.86277f,483.99412f);
		mHeartPath.cubicTo(137.11867f,420.75228f,125.72108f,411.5999f,91.719238f,380.29088f);
		mHeartPath.cubicTo(29.03471f,322.57071f,2.413622f,264.58086f,2.5048478f,185.95124f);
		mHeartPath.cubicTo(2.5493594f,147.56739f,5.1656152f,132.77929f,15.914734f,110.15398f);
		mHeartPath.cubicTo(34.151433f,71.768267f,61.014996f,43.244667f,95.360052f,25.799457f);
		mHeartPath.cubicTo(119.68545f,13.443675f,131.6827f,7.9542046f,172.30448f,7.7296236f);
		mHeartPath.cubicTo(214.79777f,7.4947896f,223.74311f,12.449347f,248.73919f,26.181459f);
		mHeartPath.cubicTo(279.1637f,42.895777f,310.47909f,78.617167f,316.95242f,103.99205f);
		
		mHeartPath.lineTo(320.95052f,119.66445f);
		mHeartPath.lineTo(330.81015f,98.079942f);
		
		mHeartPath.cubicTo(386.52632f,-23.892986f,564.40851f,-22.06811f,626.31244f,101.11153f);
		mHeartPath.cubicTo(645.95011f,140.18758f,648.10608f,223.6247f,630.69256f,270.6244f);
		mHeartPath.cubicTo(607.97729f,331.93377f,565.31255f,378.67493f,466.68622f,450.30098f);
		mHeartPath.cubicTo(402.0054f,497.27462f,328.80148f,568.34684f,323.70555f,578.32901f);
		mHeartPath.cubicTo(317.79007f,589.91654f,323.42339f,580.14491f,297.29747f,550.86823f);
		
		mHeartPath.close();
		
	}
*/
	private ValueAnimator valueAnimator;
	private float progress = 1.6f;
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		valueAnimator = ValueAnimator.ofFloat(-3.0f,3.0f);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				progress = (float) animation.getAnimatedValue();
				invalidate();
			}
		});
		valueAnimator.setDuration(6000);
		valueAnimator.setRepeatCount(Animation.INFINITE);
		valueAnimator.start();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		valueAnimator.end();
		super.onDetachedFromWindow();
	}
}
