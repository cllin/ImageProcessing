package com.cllin.imageprocessing.processor;

import com.cllin.imageprocessing.processing.processor.ScriptC_binarize;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;

public class BinarizeProcessor extends SuperProcessor {

    public BinarizeProcessor(RenderScript renderscript) {
    	super(renderscript);
    }

    @Override
    public Bitmap process(Bitmap input) throws RSRuntimeException {
    	Bitmap output = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
    	Allocation inputAllocation = Allocation.createFromBitmap(mRenderScript, input);
    	Allocation outputAllocation = Allocation.createFromBitmap(mRenderScript, output);
    	ScriptC_binarize mBinarizeScript = new ScriptC_binarize(mRenderScript);
    		
    	mBinarizeScript.forEach_root(inputAllocation, outputAllocation);
    	outputAllocation.copyTo(output);
    		
    	return output;
    }
}
