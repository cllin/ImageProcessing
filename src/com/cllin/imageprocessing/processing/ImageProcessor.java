package com.cllin.imageprocessing.processing;


import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.RenderScript;

import com.cllin.imageprocessing.helper.Processor;
import com.cllin.imageprocessing.processing.processor.BinarizeProcessor;
import com.cllin.imageprocessing.processing.processor.GrayScaleProcessor;
import com.cllin.imageprocessing.processing.processor.SobelProcessor;
import com.cllin.imageprocessing.processing.processor.SuperProcessor;

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
	
	public Bitmap process(Processor processorType, Bitmap input) throws Exception {
		if (mRS == null) throw new Exception("The processor is not properly initialized");
		
		Bitmap output = null;
		SuperProcessor processor;
		
		switch (processorType) {
		case BINARIZE:
			processor = new BinarizeProcessor(mRS);
			output = processor.process(input);
			break;
		case SOBEL:
			processor = new SobelProcessor(mRS);
			output = processor.process(input);
			break;
		case GRAYSCALE:
			processor = new GrayScaleProcessor(mRS);
			output = processor.process(input);
			break;
		}
		
		return output;
	}
}
