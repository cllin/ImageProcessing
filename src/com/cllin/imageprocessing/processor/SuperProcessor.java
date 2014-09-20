package com.cllin.imageprocessing.processor;

import android.graphics.Bitmap;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;

public abstract class SuperProcessor {
    protected RenderScript mRenderScript;
	
    public abstract Bitmap process(Bitmap input) throws RSRuntimeException;
    
    public SuperProcessor(RenderScript renderscript) {
	this.mRenderScript = renderscript;
    }
	
    public RenderScript getScript() {
	return mRenderScript;
    }
}
