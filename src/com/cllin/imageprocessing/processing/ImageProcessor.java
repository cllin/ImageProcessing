package com.cllin.imageprocessing.processing;


import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import com.cllin.imageprocessing.helper.Processor;

public class ImageProcessor {
	
	private static ImageProcessor processor = null;
	
    private RenderScript mRS;
	
	private ImageProcessor(Context context) {
		mRS = RenderScript.create(context);
	}
	
	public static ImageProcessor getProcessor(Context context) {
		if (processor == null) processor = new ImageProcessor(context);
		
		return processor;
	}
	
	public Bitmap process(Processor processor, Bitmap input) throws Exception {
		if (mRS == null) throw new Exception("The processor is not properly initialized");
		
		Bitmap output = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
		Allocation inputAllocation = Allocation.createFromBitmap(mRS, input);
		Allocation outputAllocation = Allocation.createFromBitmap(mRS, output);
		
		switch (processor) {
		case BINARIZE:
			
			ScriptC_binarize mBinarizeScript = new ScriptC_binarize(mRS);
			mBinarizeScript.forEach_root(inputAllocation, outputAllocation);
			break;
		case SOBEL:
			
			ScriptC_sobel mSobelScript = new ScriptC_sobel(mRS);
			mSobelScript.forEach_root(inputAllocation, outputAllocation);
			break;
		case GRAYSCALE:
			
			ScriptC_grayscale mGrayScaleScript = new ScriptC_grayscale(mRS);
			mGrayScaleScript.forEach_root(inputAllocation, outputAllocation);
			break;
		}
		
		outputAllocation.copyTo(output);
		return output;
	}
}
