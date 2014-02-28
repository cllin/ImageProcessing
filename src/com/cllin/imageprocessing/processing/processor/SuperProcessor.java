package com.cllin.imageprocessing.processing.processor;

import android.graphics.Bitmap;
import android.renderscript.RenderScript;

public class SuperProcessor {
	protected RenderScript mRenderScript;
	
	public SuperProcessor(RenderScript renderscript) {
		this.mRenderScript = renderscript;
	}
	
	public Bitmap process(Bitmap input) {
		return input;
	}
	
	public RenderScript getScript() {
		return mRenderScript;
	}
}
