package com.taotaohai.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	/**
	 * 表示不中断此事件，让子view接收触摸事件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
	
	/**
	 * 不作任何处理，屏蔽viewpager的滑动操作
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



}
