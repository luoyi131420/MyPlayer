package com.kt.localmedia.pic.anim;

import com.kt.localmedia.pic.ITurnPage;
import com.kt.localmedia.util.KTBitmapsf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * 
 * @author yanglonghui
 *
 */
public class ShutterDown2Up implements ITurnPage {

	private int duration=100;//动画持续时间
	private final int leafNum=7;
	private PaintFlagsDrawFilter pdf=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	private int bitmapW;
	private int bitmapH;
	private Bitmap newBitmap;
	public ShutterDown2Up() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onTurnPageDraw(SurfaceHolder holder, Bitmap[] bitmap,
			int maxWidth, int maxHeight) {
		
       KTBitmapsf ktsf = new KTBitmapsf();
		
		if(bitmap[0].getWidth()>maxWidth){
			newBitmap =ktsf.smallW(bitmap[0],maxWidth);
		}
		else if(bitmap[0].getHeight()>maxHeight){
			newBitmap =ktsf.smallH(bitmap[0],maxHeight);
		}
		else{
			newBitmap =ktsf.getMBitmap(bitmap[0]);
		}
	
		bitmapW =newBitmap.getWidth();
		bitmapH = newBitmap.getHeight();
		int dx=(maxWidth-bitmapW)/2;
		int dy=(maxHeight-bitmapH)/2;
		int perHeight=bitmapH/leafNum;
		
		long start=System.currentTimeMillis();
		long runMills=0;
		
		Rect src=new Rect();
		Canvas canvas=null;
		boolean isRunning=true;
		while(isRunning)
		{
			isRunning=((runMills=(System.currentTimeMillis()-start))<duration);
			if(!isRunning)
			{
				runMills=duration;
			}
			
			try {
					canvas=holder.lockCanvas(null);
					
					canvas.setDrawFilter(pdf);
					canvas.drawColor(Color.BLACK);// 清除画布
					
					canvas.save();
					canvas.translate(dx, dy);
					for(int j=0;j<leafNum;j++)
					{
						src.set(0, (int)((j+1)*perHeight-((float)runMills/(float)duration)*perHeight), bitmapW, (j+1)*perHeight);
						canvas.drawBitmap(newBitmap, src, src, null);
					}
					canvas.restore();
					
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null!=canvas)
				{
					holder.unlockCanvasAndPost(canvas);
				}
				else
				{
					break;
				}
				
				if(!isRunning)
				{
					break;
				}
			}
		}
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub

	}

}
