package com.cllin.imageprocessing.processor;


import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;

import com.cllin.imageprocessing.activity.exceptions.ProcessorException;

public class ProcessorInterface {
	
    private static ProcessorInterface processor = null;
    private RenderScript mRS;
	
//  use singleton pattern to ensure there is only one processor
    private ProcessorInterface(Context context) {
	mRS = RenderScript.create(context);
    }
	
    public static ProcessorInterface getProcessor(Context context) {
	if (processor == null) processor = new ProcessorInterface(context);
	
	return processor;
    }
	
    public Bitmap process(ProcessorType processorType, Bitmap input) throws ProcessorException {
	if (mRS == null) throw new ProcessorException(ProcessorException.NOT_INITIALIZED);
		
	Bitmap output = null;
	SuperProcessor processor;
	
	switch (processorType) {
	case BINARIZE:
	    processor = new BinarizeProcessor(mRS);
	    break;
	case SOBEL:
	    processor = new SobelProcessor(mRS);
	    break;
	case GRAYSCALE:
	    processor = new GrayScaleProcessor(mRS);
	    break;
	default:
	    throw new ProcessorException(ProcessorException.UNKNOWN_PROCESSOR_TYPE);
	}
	
	try {
	    output = processor.process(input);
	} catch (RSRuntimeException e) {
	    e.printStackTrace();
	    throw new ProcessorException(ProcessorException.RUNTIME_EXCEPTION);
	}
	
	return output;
    }
	
    public enum ProcessorType {
	BINARIZE, GRAYSCALE, SOBEL
    }
}
