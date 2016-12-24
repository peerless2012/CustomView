package com.peerless2012.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class ShaderView extends View{
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public ShaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setTextSize(25);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Style.FILL);
		canvas.translate(getWidth() / 2, getHeight() / 2);
		int startColor = Color.parseColor("#2F00FFF0");
		int endColor = Color.TRANSPARENT;
		RadialGradient radialGradient = 
				new RadialGradient(0, 0, 200, startColor, endColor, TileMode.CLAMP);
		mPaint.setShader(radialGradient);
		canvas.drawCircle(0, 0, 300, mPaint);
	}
}
