/**
 * 
 */
package com.example.androidtest.view;

import java.util.Random;

import com.example.androidtest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author luoyuan.myp
 * 
 *         2014-5-15
 */
public class LoadingAnimationView extends View {

	private Context context;
	private int process = 0;
	private int max = 100;
	private Bitmap mBD;

	Paint mArcPaint = new Paint();
	private float mBMLeft = 110;
	private float mBMRight = 110;

	private int mHandledBMLeft = 110;
	private int mHandledBMRight = 110;
	Random mRandom = new Random();

	public LoadingAnimationView(Context c, AttributeSet arr) {
		super(c, arr);
		this.context = c;
		initOP();
		initBGPaint();

	}

	Thread canvasThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
					try {
						Thread.sleep(100);
						mBMLeft++;
						mBMRight++;
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
					postInvalidate();

				// 更新界面
			}
			// mBMLeft = mRandom.nextInt(200);
			// mBMRight = mRandom.nextInt(200);
			// while (true) {
			//
			// }
		}
	});
	private Bitmap handledBitmap;
	private Matrix mMatrix = new Matrix();
	private Matrix mTranslateMatrix = new Matrix();
	private Bitmap mLaugherBM;

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		drawCicyle(canvas);
	}

	private void initBGPaint() {
		mBD = BitmapFactory.decodeResource(context.getResources(), R.drawable.women);
		mLaugherBM = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

	}

	private void initOP() {
		mArcPaint.setColor(Color.BLUE);
		mArcPaint.setStyle(Paint.Style.FILL);
	}

	private void drawCicyle(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.YELLOW);
		p.setStyle(Paint.Style.FILL);

		canvas.drawCircle(150, 105, 100, mArcPaint);
		canvas.drawLine(0, 0, 500, 500, p);
		canvas.drawBitmap(mBD, mBMLeft, mBMRight, mArcPaint);
		
				handledBitmap = Bitmap.createBitmap(mBD, 0, 0, mBD.getWidth(),      
						 mBD.getHeight(), mMatrix, true);
		
		mMatrix.setScale(100f/mBD.getWidth(), 100f/mBD.getHeight());
		//平移到（100，100）处
//		mMatrix.postTranslate(100, 100);
		//倾斜x和y轴，以（100，100）为中心。
//		mMatrix.postSkew(0.2f, 0.2f, 100, 100);
		
		canvas.drawBitmap(mLaugherBM, mTranslateMatrix, null);
		if(mBMLeft <600){
			mBMLeft += 2;
			mBMRight+=2;
			mMatrix.setRotate(mBMLeft);
			mTranslateMatrix.setTranslate(mBMLeft, mBMLeft);
			postInvalidateDelayed(20);
		}
	}

}
