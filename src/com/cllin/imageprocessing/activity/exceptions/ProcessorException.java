package com.cllin.imageprocessing.activity.exceptions;

@SuppressWarnings("serial")
public class ProcessorException extends Exception {

    private static final String DEFAULT_MESSAGE = "Unknown exception happens to processor";
    
    public static final String NOT_INITIALIZED = "The processor is not properly initialized";
    public static final String UNKNOWN_PROCESSOR_TYPE = "Cannot resolve processot type";
    public static final String RUNTIME_EXCEPTION = "Processor exception in runtime";
    
    public ProcessorException() {
	super(DEFAULT_MESSAGE);
    }
    
    public ProcessorException(String message) {
	super(message);
    }
}
