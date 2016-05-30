package com.peerless2012.customerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年5月30日 下午10:59:13
* @Version V1.0
* @Description: 自定义话筒录音的View （drawable下wifi）
*/
public class WifiView extends View {
	public WifiView(Context context) {
		this(context, null);
	}

	public WifiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WifiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

	}

}
