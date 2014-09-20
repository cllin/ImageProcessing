package com.cllin.imageprocessing.processor;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;

import com.cllin.imageprocessing.processing.processor.ScriptC_sobel;

public class SobelProcessor extends SuperProcessor {

    public SobelProcessor(RenderScript renderscript) {
	super(renderscript);
    }
	
    @Override
    public Bitmap process(Bitmap input) throws RSRuntimeException {
	Bitmap output = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
	
	ScriptC_sobel mSobelScript = new ScriptC_sobel(mRenderScript);
	Allocation inputAllocation = createAllocation(input);
	Allocation outputAllocation = Allocation.createTyped(mRenderScript, inputAllocation.getType());
		
	mSobelScript.set_gIn(inputAllocation);
	mSobelScript.set_gOut(outputAllocation);
	mSobelScript.set_mImageHeight(input.getHeight());
	mSobelScript.set_mImageWidth(input.getWidth());
	mSobelScript.set_gScript(mSobelScript);
	mSobelScript.bind_gOutPixels(outputAllocation);
	
	mSobelScript.invoke_compute();
	
	output = getBitmapFromAllocation(outputAllocation, output);
	return output;
    }
	
    private Allocation createAllocation(Bitmap bitmap) {
	int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
	bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	
	Allocation allocation = Allocation.createSized(mRenderScript, 
			Element.I32(mRenderScript), bitmap.getWidth() * bitmap.getHeight(), Allocation.USAGE_SCRIPT);
	allocation.copyFrom(pixels);
	
	return allocation;
    }
	
    private Bitmap getBitmapFromAllocation(Allocation allocation, Bitmap bitmap) {
	int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
	allocation.copyTo(pixels);
	bitmap.setPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	
	return bitmap;
    } 
}
