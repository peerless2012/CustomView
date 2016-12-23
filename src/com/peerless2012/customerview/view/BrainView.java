package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

public class BrainView extends View{
	
	private Paint mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private Path mPath = new Path();
	
	private SumPathEffect mSumPathEffect;
	
	public BrainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		CornerPathEffect cornerPathEffect = new CornerPathEffect(5);
//		mSumPathEffect = new SumPathEffect(cornerPathEffect, second)
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mPath.reset();
		
		mPath.moveTo(20, 20);
		mPath.lineTo(w / 2, 20);
		mPath.lineTo(w - 20, h / 2);
		mPath.lineTo(w - 20, h - 20);
		mPath.lineTo(w / 2, h - 20);
		mPath.lineTo(20, h /2 );
		mPath.close();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPathPaint.setColor(Color.argb(200, 200, 20, 40));
		mPathPaint.setStrokeWidth(15);
		mPathPaint.setStyle(Style.FILL);
		mPathPaint.setStrokeCap(Cap.ROUND);
//		mPathPaint.setStrokeJoin(Join.ROUND);
		
		// 路径效果
		CornerPathEffect cornerPathEffect = new CornerPathEffect(10);
		mPathPaint.setPathEffect(cornerPathEffect);
		
		mPathPaint.setMaskFilter(new BlurMaskFilter(20, Blur.SOLID));
		
		canvas.drawPath(mPath, mPathPaint);
		
	}

}
