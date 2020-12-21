package com.kyhsgeekcode.tonechanger;

import android.graphics.*;
import android.util.*;
import java.io.*;
import java.nio.*;

public class ToneChanger
{
	public static void Run(MainActivity a)
	{
		String path = "";
		File file=new File("");
		//File logfile=new File("/sdcard/fixzip.log");
		
		//boolean archive=false;
		do{	
			a.print("Enter the path");
			try
			{
				path = a.readLine();
			}
			catch (InterruptedException e)
			{
				a.print(Log.getStackTraceString(e));
			}
			file = new File(path);
		}while(!file.exists());
		a.print("reading");
		Bitmap bit=BitmapFactory.decodeFile(path);
		if(bit==null)
		{
			a.print("Failed to decode image");
			return;
		}
		int w=bit.getWidth();
		int h= bit.getHeight();
		int[] pixels=new int[w*h];
		bit.getPixels(pixels,0,w,0,0,w,h);
		a.print("read done");
		int[] outpixels=new int[w*h];
		for(int i=0;i<pixels.length;++i)
		{
			int r=Color.red(pixels[i]);
			int g=Color.green(pixels[i]);
			int b=Color.blue(pixels[i]);
			if(r<240||g<240||b<240)
			{
				outpixels[i]=Color.RED;
			}
			else{
				outpixels[i]=Color.WHITE;
			}
		}
		a.print("Saving");
		Bitmap outbit = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		// vector is your int[] of ARGB 
		outbit.copyPixelsFromBuffer(IntBuffer.wrap(outpixels));
		try
		{
			FileOutputStream outf=new FileOutputStream("/sdcard/hello"+file.getName()+".png");
			outbit.compress(Bitmap.CompressFormat.PNG,100,outf);
		}
		catch (FileNotFoundException e)
		{
			a.print(Log.getStackTraceString(e));
		}
		a.print("Finish");
	}
}
