package com.cllin.imageprocessing.processor;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import com.cllin.imageprocessing.processing.processor.ScriptC_grayscale;

public class GrayScaleProcessor extends SuperProcessor {

    public GrayScaleProcessor(RenderScript renderscript) {
	super(renderscript);
    }

    @Override
    public Bitmap process(Bitmap input) {
	Bitmap output = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
	Allocation inputAllocation = Allocation.createFromBitmap(mRenderScript, input);
	Allocation outputAllocation = Allocation.createFromBitmap(mRenderScript, output);
	ScriptC_grayscale mGrayScaleScript = new ScriptC_grayscale(mRenderScript);
		
	mGrayScaleScript.forEach_root(inputAllocation, outputAllocation);
	outputAllocation.copyTo(output);
	
	return output;
    }
}
